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

@XmlRootElement(name = "member")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "member")
public class Member implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@XmlElement
	@OneToOne
	private Person person;
	
	@XmlElement
	@Column(name = "mail")
	private String mail;
	
	@XmlElement
	@Column(name = "function")
	private String function;
	
	@OneToOne
	private Stb stb;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public String getMail() {
		return mail;
	}

	public String getFunction() {
		return function;
	}

	public Stb getStb() {
		return stb;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public void setStb(Stb stb) {
		this.stb = stb;
	}
	
	

}
