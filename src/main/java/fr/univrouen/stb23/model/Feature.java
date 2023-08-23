package fr.univrouen.stb23.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "feature")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "feature")
public class Feature implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feature_id")
	private Long id;
	
	@XmlElement
	@Column(name = "name")
	private String name;
	
	@XmlElement
	@Column(name = "number")
	private Integer number;
	
	@XmlElement
	@Column(name = "section")
	private String section;
	
	@XmlElement
	@Column(name = "description")
	private String description;
	
	@XmlElement
	@Column(name = "priority")
	private Integer priority;
	
	@OneToOne
	private Stb stb;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getNumber() {
		return number;
	}

	public String getSection() {
		return section;
	}

	public String getDescription() {
		return description;
	}

	public Integer getPriority() {
		return priority;
	}

	public Stb getStb() {
		return stb;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setStb(Stb stb) {
		this.stb = stb;
	}
	  
	 
}
