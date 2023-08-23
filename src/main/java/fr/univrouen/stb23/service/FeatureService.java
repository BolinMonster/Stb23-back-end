package fr.univrouen.stb23.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univrouen.stb23.model.Feature;
import fr.univrouen.stb23.repository.FeatureRepository;

@Service
public class FeatureService {
	
	@Autowired
	private FeatureRepository featureRepository;

	public Iterable<Feature> getFeatures() {
		return featureRepository.findAll();
	}
	
	public long getNumberOfFeature() {
		return featureRepository.count();
	}

	public Optional<Feature> getFeatureById(Long id) {
		return featureRepository.findById(id);
	}

	public Feature addFeature(Feature feature) {
		return featureRepository.save(feature);
	}
	
	public Iterable<Feature> addFeatures(Iterable<Feature> clients) {
		return featureRepository.saveAll(clients);
	}
	
	public void removeStb(Feature feature) {
		featureRepository.delete(feature);
	}
	
	public void removeStbById(Long id) {
		featureRepository.deleteById(id);
	}
	
	public void removeAll() {
		featureRepository.deleteAll();
	}

}
