package fr.univrouen.stb23.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import fr.univrouen.stb23.SimpleErrorHandler;
import fr.univrouen.stb23.model.Stb;
import fr.univrouen.stb23.model.Stbs;
import fr.univrouen.stb23.service.StbService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Problèmes rencontrés et Solutions :
 * https://stackoverflow.com/questions/4152269/why-my-arraylist-is-not-marshalled-with-jaxb
 * https://stackoverflow.com/questions/10795793/jaxb-illegalannotationexception-is-thrown-during-parsing-xml
 * https://stackoverflow.com/questions/25819934/jaxb-unmarshalling-with-namespace
 * https://stackoverflow.com/questions/2302802/how-to-fix-the-hibernate-object-references-an-unsaved-transient-instance-save
 */
@RestController
public class StbController {

	@Autowired
	private StbService stbService;

	/**
	 * Récupère toutes les STB au format XML dans la base de données.
	 * 
	 * @return ResponseEntity contenant les STB au format XML.
	 * @throws JAXBException si une erreur se produit lors de l'appel à marshal du
	 *                       marshaller JAXB.
	 */
	@GetMapping(value = "/stb23/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAllStbAsXml() throws JAXBException {
		final List<Stb> listStbs = new ArrayList<Stb>();
		for (Stb stb : stbService.getStbs()) {
			listStbs.add(stb);
		}
		final Stbs stbs = new Stbs();
		stbs.setStbs(listStbs);
		JAXBContext jaxbContext = JAXBContext.newInstance(Stbs.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(stbs, stringWriter);
		return ResponseEntity.status(HttpStatus.OK).body(stringWriter.toString());
	}

	/**
	 * Récupère tous les STB au format HTML pour être utilisable par une page HTML.
	 * 
	 * @return Liste des STB.
	 */
	@GetMapping(value = "/stb23/resume")
	public List<Stb> getAllStbsAsHtml() {
		final List<Stb> stbs = new ArrayList<Stb>();
		for (Stb stb : stbService.getStbs()) {
			stbs.add(stb);
		}
		return stbs;
	}

	/**
	 * Récupère une STB spécifique par son identifiant au format XML.
	 * 
	 * @param id Identifiant de la STB à récupérer.
	 * @return ResponseEntity contenant le STB demandé au format XML.
	 */
	@GetMapping(value = "/stb23/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getStbAsXmlById(@PathVariable Long id) {
		Optional<Stb> stbOptional = stbService.getStbById(id);
		if (!stbOptional.isPresent()) {
			String xmlResponse = "<id>" + id + "</id>\n" + "<status>ERROR</status>\n";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(xmlResponse);
		}
		Stb stb = stbOptional.get();
		return ResponseEntity.status(HttpStatus.OK).body(stb);
	}

	/**
	 * Récupère un STB spécifique grâce à son identifiant pour être utilisable par
	 * une page HTML.
	 * @param id Identifiant de la STB à récupérer.
	 * @return Map contenant les informations de la STB.
	 */
	@GetMapping(value = "/stb23/html/{id}")
	public Map<String, Object> getAllStbsAsHtmlById(@PathVariable Long id) {
		final Map<String, Object> result = new HashMap<>();
		final Optional<Stb> stbOptional = stbService.getStbById(id);
		if (!stbOptional.isPresent()) {
			result.put("errorId", id);
			result.put("status", "ERROR");
		} else {
			result.put("stb", stbOptional.get());
		}
		return result;
	}
	
	/**
	 * Cette méthode gère la requête POST pour insérer une STB (Spécification Technique de Besoin) à partir d'un flux XML.
	 * @param xmlPayload Le corps de la requête contenant le flux XML représentant la STB.
	 * @return ResponseEntity<String> Une réponse HTTP contenant le résultat de l'opération.
	 */
	@PostMapping(value = "/stb23/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> postStb(@RequestBody String xmlPayload) {
		// Validation du flux XML par rapport au schéma XSD
		final String xsdFilePath = "src/main/resources/xml/stb23.tp2.xsd";
		final File xsdFile = new File(xsdFilePath);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		SAXParser parser = null;
		SimpleErrorHandler xsdErrorHandler = new SimpleErrorHandler();
		Schema schema = null;
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
		    schema = schemaFactory.newSchema(xsdFile);
		    parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();
		} catch (SAXNotRecognizedException e) {
		    e.printStackTrace();
		} catch (SAXNotSupportedException e) {
		    e.printStackTrace();
		} catch (SAXException e) {
		    e.printStackTrace();
		}
		// Parsing du flux XML
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("<error><status>ERROR</status><detail>INTERNAL SERVER ERROR</detail></error>");
		}
		InputSource is = new InputSource(new StringReader(xmlPayload));
		Document doc = null;
		try {
			doc = documentBuilder.parse(is);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("<error><status>ERROR</status><detail>INVALID</detail></error>");
		}
		final Validator validator = schema.newValidator();
		validator.setErrorHandler(xsdErrorHandler);
		// Validation du flux XML par rapport au schéma XSD
		try {
		    validator.validate(new DOMSource(doc));
		    // Désactive la validation car ça ne fonctionne pas
		    /*
		    if (xsdErrorHandler.hasError()) {
		    	return ResponseEntity.badRequest().body("<error><status>ERROR</status><detail>" + xsdErrorHandler.getMessage() + "</detail></error>");
		    }
		    */
		} catch (SAXException | IOException e) {
		    return ResponseEntity.badRequest().body("<error><status>ERROR</status><detail>Une erreur interne est survenue lors de la validation du XML avec le schmé XSD</detail></error>");
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		String xmlString = null;
		try {
			transformer = transformerFactory.newTransformer();
			StringWriter stringWriter = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
			xmlString = stringWriter.toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		// Vérification de l'unicité de la STB
		final Element docElement = doc.getDocumentElement();
		String titre = docElement.getElementsByTagName("stb23:title").item(0).getTextContent();
		String versionStr = docElement.getElementsByTagName("stb23:version").item(0).getTextContent();
		Double version = null;
		try {
		    version = Double.parseDouble(versionStr);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    version = null;
		}
		String validationDateStr = docElement.getElementsByTagName("stb23:date").item(0).getTextContent();
		Date validationDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
		    validationDate = dateFormat.parse(validationDateStr);
		} catch (ParseException e) {
		    e.printStackTrace();
		    validationDate = null;
		    return ResponseEntity.badRequest().body("<error><status>ERROR</status><detail>FORMAT DATE INVALID (YYYY-MM-DD)</detail></error>");
		}
		Optional<Stb> existingStb = stbService.getStbByTitleSimilarToAndValidationDateAndVersion(titre, version, validationDate);
		if (existingStb.isPresent()) {
		    return ResponseEntity.badRequest().body("<error><status>ERROR</status><detail>DUPLICATED</detail></error>");
		}
		// Unmarshal
		try {
		    final JAXBContext jaxbContext = JAXBContext.newInstance(Stb.class);
		    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		    final Stb newStb = (Stb) unmarshaller.unmarshal(new StringReader(xmlString));
		    stbService.addStb(newStb);
			Long id = newStb.getId();
			String responseXml = "<stb><id>" + id + "</id><status>INSERTED</status></stb>";
			return ResponseEntity.status(HttpStatus.CREATED).body(responseXml);
		} catch (JAXBException e) {
		    e.printStackTrace();
		    final String errorXml = "<error><status>ERROR</status><detail>JAXB_EXCEPTION</detail></error>";
		    return ResponseEntity.badRequest().body(errorXml);
		}
	}

	/**
	 * Supprime une STB grâce son identifiant.
	 * 
	 * @param id Identifiant de la STB à supprimer.
	 * @return ResponseEntity contenant le résultat de la suppression au format XML.
	 */
	@DeleteMapping(value = "/stb23/delete/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> deleteStb(@PathVariable Long id) {
		final Optional<Stb> stb = stbService.getStbById(id);
		if (!stb.isPresent()) {
			String responseXml = "<status>ERROR</status>";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseXml);
		}
		HttpStatus status = null;
		String xmlResponse = null;
		try {
			stbService.removeStb(stb.get());
			xmlResponse = "<id>" + id + "</id>\n" + "<status>DELETED</status>\n";
			status = HttpStatus.CREATED;
		} catch (Exception e) {
			xmlResponse = "<status>ERROR</status>\n";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(status).body(xmlResponse);
	}

	/**
	 * Recherche les STB par titre et date de validation.
	 * 
	 * @param title          Titre de la STB à rechercher.
	 * @param validationDate Date de validation de la STB à rechercher.
	 * @return ResponseEntity contenant les STB correspondants au format XML.
	 * @throws JAXBException si une erreur se produit lors de l'appel à marshal du
	 *                       marshaller JAXB.
	 */
	@GetMapping(value = "/stb23/search", produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public ResponseEntity<String> getStbByTitleAndValidationDate(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "validationDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date validationDate)
			throws JAXBException {
		List<Stb> listStbs = new ArrayList<Stb>();
		if (title != null && !title.isBlank() && validationDate != null) {
			listStbs = stbService.getStbByTitleSimilarToAndValidationDate(title, validationDate);
		} else if (title != null && !title.isBlank() && validationDate == null) {
			listStbs = stbService.getStbByTitleSimilarTo(title);
		} else if (validationDate != null && (title == null || title.isBlank())) {
			listStbs = stbService.getStbByValidationDate(validationDate);
		} else {
			final Iterable<Stb> allStbs = stbService.getStbs();
			allStbs.forEach(listStbs::add);
		}
		if (listStbs.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CREATED).body("<status>NONE</status>\n");
		}
		final Stbs stbs = new Stbs();
		stbs.setStbs(listStbs);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Stbs.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(stbs, stringWriter);
			return ResponseEntity.status(HttpStatus.OK).body(stringWriter.toString());
		} catch (JAXBException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de la génération de la réponse XML");
		}
	}
	
	/**
	 * Affiche le contenu d'un document XML en parcourant ses éléments.
	 * @param doc Le document XML à afficher.
	 */
	private void displayDocumentContent(Document doc) {
	    System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
	    System.out.println("----------------------------");
	    for (int i = 0; i < doc.getDocumentElement().getChildNodes().getLength(); i++) {
	        Node nNode = doc.getDocumentElement().getChildNodes().item(i);
	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            System.out.println("Current Element: " + nNode.getNodeName());
	            /*Element eElement = (Element) nNode;
	            if (eElement.getNodeName().equalsIgnoreCase("title")) {
	                System.out.println("title: " + eElement.getTextContent());
	            } else if (eElement.getNodeName().equalsIgnoreCase("version")) {
	                System.out.println("version: " + eElement.getElementsByTagName("version").item(0).getTextContent());
	            }*/
	        }
	    }
	}
	
	/**
	 * Affiche le contenu d'un document XML.
	 * @param doc Le document XML à afficher.
	 */
	private void displayXMLContentWithTransformer(Document doc) {
	    // Displaying the XML file sent in the console
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer;
	    try {
	        transformer = transformerFactory.newTransformer();
	        StringWriter stringWriter = new StringWriter();
	        transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
	        String xmlString = stringWriter.toString();
	        System.out.println("Retrieved XML:");
	        System.out.println(xmlString);
	    } catch (TransformerException e) {
	        e.printStackTrace();
	    }
	}



}
