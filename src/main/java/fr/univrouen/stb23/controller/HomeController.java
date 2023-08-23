package fr.univrouen.stb23.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.stb23.service.ClientService;
import fr.univrouen.stb23.service.FeatureService;
import fr.univrouen.stb23.service.StbService;

@RestController
public class HomeController {
	
	@Autowired
	private StbService stbService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private FeatureService featureService;
	
	/**
	 * Récupère les informations de la page d'accueil.
	 * @return ResponseEntity contenant les informations de la page d'accueil.
	 */
	@GetMapping(value = {"/", "/home"})
	public ResponseEntity<Map<String, Object>> getHome() {
	    final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
	    final String today = format.format(new Date());
	    final Map<String, Object> attributes = new HashMap<>();
	    {
	        attributes.put("projectName", "stb23");
	        attributes.put("versionNumber", "1.0");
	        attributes.put("developerName", "Nom Prénom");
	        attributes.put("collegeLogo",
	                "https://upload.wikimedia.org/wikipedia/commons/d/df/Universit%C3%A9_de_Rouen.png");
	        attributes.put("currentDate", today);
	        attributes.put("numberOfStb", stbService.getNumberOfStb());
	        attributes.put("numberOfClient", clientService.getNumberOfClient());
	        attributes.put("numberOfFeature", featureService.getNumberOfFeature());

	    }
	    return ResponseEntity.ok().body(attributes);
	}

}