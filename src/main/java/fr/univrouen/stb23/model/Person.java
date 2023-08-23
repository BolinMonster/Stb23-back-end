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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "personne")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long id;
	
	@XmlAttribute(name = "gender")
	@Column(name = "gender")
	private String gender;
	
	@XmlAttribute(name = "lastname")
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "value")
	private String value;
	
	@OneToOne
	private Client client;
	
	@OneToOne
	private Member membre;

	public Long getId() {
		return id;
	}

	public String getGender() {
		return gender;
	}

	public String getLastname() {
		return lastname;
	}

	public String getValue() {
		return value;
	}

	public Client getClient() {
		return client;
	}

	public Member getMembre() {
		return membre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setMembre(Member membre) {
		this.membre = membre;
	}


}
