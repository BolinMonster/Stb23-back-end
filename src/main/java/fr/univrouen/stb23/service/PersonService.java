package fr.univrouen.stb23.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univrouen.stb23.model.Person;
import fr.univrouen.stb23.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	public Iterable<Person> getPersons() {
		return personRepository.findAll();
	}

	public Optional<Person> getPersonById(Long id) {
		return personRepository.findById(id);
	}

	public Person addPerson(Person person) {
		return personRepository.save(person);
	}
	
	public Iterable<Person> addPersons(Iterable<Person> persons) {
		return personRepository.saveAll(persons);
	}
	
	public void removeStb(Person person) {
		personRepository.delete(person);
	}
	
	public void removeStbById(Long id) {
		personRepository.deleteById(id);
	}
	
	public void removeAll() {
		personRepository.deleteAll();
	}

}
