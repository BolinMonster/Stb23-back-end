package fr.univrouen.stb23;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {

	private boolean errorOccured;
	private String message;

	public SimpleErrorHandler() {
		this.errorOccured = false;
	}

	public boolean hasError() {
		return this.errorOccured;
	}
	
	public String getMessage() {
		return this.message;
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		this.errorOccured = true;
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		this.errorOccured = true;
		this.message = "Le contenu XML fournit ne respecte pas le schéma XSD : " + exception.getMessage();
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		this.errorOccured = true;
		this.message = "Erreur fatale lors de la validation XSD : " + exception.getMessage();
	}
	
}