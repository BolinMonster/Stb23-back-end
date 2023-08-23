package fr.univrouen.stb23.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;

/**
 * https://stackoverflow.com/questions/4152269/why-my-arraylist-is-not-marshalled-with-jaxb
 * https://stackoverflow.com/questions/10795793/jaxb-illegalannotationexception-is-thrown-during-parsing-xml
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Features {

	@XmlElement(name = "feature")
	List<Feature> features = new ArrayList<Feature>();

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

}
