package fr.univrouen.stb23.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(namespace = "http://univrouen.fr/stb23", name = "stb")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
	    "title",
	    "version",
	    "validationDate",
	    "description",
	    "client",
	    "members",
	    "features"
})
@Entity
@Table(name = "stb")
public class Stb implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stb_id")
	private Long id;
	
	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "title")
	@Column(name = "title")
	private String title;
	
	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "version")
	@Column(name = "version")
	private Double version;
	
	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "date")
	@Column(name = "validationDate")
	private Date validationDate;

	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "description")
	@Column(name = "description")
	private String description;

	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "client")
	@OneToOne(cascade = {CascadeType.ALL})
	private Client client;
	
	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "team")
	@ManyToMany(cascade = {CascadeType.ALL})
	private List<Member> members;
	
	@XmlElement(namespace = "http://univrouen.fr/stb23", name = "features")
	@ManyToMany(cascade = {CascadeType.ALL})
	private List<Feature> features;
	
	public Stb() {
		
	}

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public Double getVersion() {
		return version;
	}

	public Date getValidationDate() {
		return validationDate;
	}

	public String getDescription() {
		return description;
	}

	public Client getClient() {
		return client;
	}

	public List<Member> getMembers() {
		if (members == null) {
			return new ArrayList<Member>();
		}
		return members;
	}

	public List<Feature> getFeatures() {
		if (features == null) {
			return new ArrayList<Feature>();
		}
		return features;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	@Override
	public String toString() {
	    final StringBuilder sb = new StringBuilder();
	    sb.append("Stb {");
	    sb.append("id=").append(id);
	    sb.append(", title='").append(title).append('\'');
	    sb.append(", version=").append(version);
	    sb.append(", validationDate=").append(validationDate);
	    sb.append(", description='").append(description).append('\'');
	    sb.append(", client=").append(client);
	    sb.append(", members=").append(members);
	    sb.append(", features=").append(features);
	    sb.append('}');
	    return sb.toString();
	}

	
}
