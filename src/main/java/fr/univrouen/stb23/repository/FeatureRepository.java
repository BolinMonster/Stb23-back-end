package fr.univrouen.stb23.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import fr.univrouen.stb23.model.Feature;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, Long> {
	
}
