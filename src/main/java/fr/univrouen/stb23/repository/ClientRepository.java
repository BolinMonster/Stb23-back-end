package fr.univrouen.stb23.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import fr.univrouen.stb23.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
	
}
