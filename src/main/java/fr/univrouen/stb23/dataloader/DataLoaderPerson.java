package fr.univrouen.stb23.dataloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.univrouen.stb23.model.Person;
import fr.univrouen.stb23.service.PersonService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataLoaderPerson {
	
	@Autowired
	private PersonService personService;
	
	@PostConstruct
    public void loadData() {
        final Faker faker = new Faker(new Locale("fr"));
        final List<Person> persons = new ArrayList<>();
        final int numberOfPersons = 10;
        final String genders[] = {"M.", "Mme", "Miss", "Mrs", "Mr"}; 
        for (int i = 0; i < numberOfPersons; i++) {
            Person person = new Person();
            person.setGender(faker.options().option(genders));
            person.setLastname(faker.name().lastName());
            person.setValue(faker.name().fullName());
            persons.add(person);
        }
        personService.addPersons(persons);
    }
	
    @PreDestroy
    public void removeData() {
    	personService.removeAll();
    }

}
