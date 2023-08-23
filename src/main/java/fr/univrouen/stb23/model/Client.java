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

@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "client")
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Long id;
	
	@XmlElement
	@Column(name = "entity")
	private String entity;
	
	@XmlElement
	@OneToOne
	private Person person;
	
	@XmlElement
	@Column(name = "mail")
	private String mail;
	
	@XmlElement
	@Column(name = "tel")
	private String tel;
	
	@OneToOne
	private Stb stb;

	public Long getId() {
		return id;
	}

	public String getEntity() {
		return entity;
	}

	public Person getPerson() {
		return person;
	}

	public String getMail() {
		return mail;
	}

	public String getTel() {
		return tel;
	}

	public Stb getStb() {
		return stb;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setPersonne(Person person) {
		this.person = person;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setStb(Stb stb) {
		this.stb = stb;
	}

	
	
	

}
