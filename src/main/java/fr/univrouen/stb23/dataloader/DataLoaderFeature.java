package fr.univrouen.stb23.dataloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.univrouen.stb23.model.Feature;
import fr.univrouen.stb23.service.FeatureService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataLoaderFeature {
	
	@Autowired
	private FeatureService featureService;
	
	@PostConstruct
    public void loadData() {
        final Faker faker = new Faker(new Locale("fr"));
        List<Feature> features = new ArrayList<>();
        final int numberOfFeatures = 10;
        for (int i = 0; i < numberOfFeatures; i++) {
            Feature feature = new Feature();
            feature.setName(faker.lorem().word());
            feature.setNumber(faker.number().numberBetween(1, 100));
            feature.setSection(faker.lorem().word());
            feature.setDescription(faker.lorem().sentence());
            feature.setPriority(faker.number().numberBetween(1, 10));
            features.add(feature);
        }
        featureService.addFeatures(features);
    }
	
    @PreDestroy
    public void removeData() {
    	featureService.removeAll();
    }

}
