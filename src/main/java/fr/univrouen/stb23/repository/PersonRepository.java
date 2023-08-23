package fr.univrouen.stb23.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import fr.univrouen.stb23.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
	
}
