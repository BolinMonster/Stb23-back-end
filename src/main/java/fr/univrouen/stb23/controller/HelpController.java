package fr.univrouen.stb23.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@RestController
public class HelpController {
	
	/**
	 * Récupère la liste des opérations gérées par le service REST.
	 * @return Liste des opérations gérées par le service REST.
	 */
	@GetMapping({ "/help" })
	public List<Operation> getHelp() {
		final List<Operation> operations = new ArrayList<>();
		operations.add(new Operation("/, /home", "GET", "Affiche la page d’accueil du projet."));
		operations.add(new Operation("/help", "GET", "Affiche la liste des opérations gérées par le service REST."));
		operations.add(new Operation("/stb23/resume/xml", "GET", "Affiche la liste des spécifications stockées en XML."));
		operations.add(new Operation("/stb23/resume", "GET", "Affiche la liste des spécifications stockées en HTML."));
		operations.add(new Operation("/stb23/xml/{id}", "GET", "Affiche le contenu complet de la spécification dont l’identifiant est {id} en XML."));
		operations.add(new Operation("/stb23/html/{id}", "GET", "Affiche le contenu complet de la spécification dont l’identifiant est {id} en HTML."));
		operations.add(new Operation("/stb23/insert", "POST", "Le flux reçu est validé par le schéma XSD de définition stb23 Si le flux est déjà présent, c'est à dire si les informations titre, version et date sont identiques, alors une indication d’erreur est retournée. Si l’opération est réussie, alors la STB est ajoutée à la base et sa persistance est assurée. La valeur de id est générée automatiquement, par incrémentation de la dernière valeur enregistrée. Cette valeur doit obligatoirement être unique."));
		operations.add(new Operation("/stb23/delete/{id}", "DELETE", "Suppression de la spécification dont l’identifiant est {id}."));
		return operations;
	}

	private class Operation {

		private String url;
		private String method;
		private String summary;

		public Operation(String url, String method, String summary) {
			super();
			this.url = url;
			this.method = method;
			this.summary = summary;
		}

		public String getUrl() {
			return url;
		}

		public String getMethod() {
			return method;
		}

		public String getSummary() {
			return summary;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

	}

}
