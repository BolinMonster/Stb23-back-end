package fr.univrouen.stb23.dataloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.univrouen.stb23.model.Client;
import fr.univrouen.stb23.service.ClientService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataLoaderClient {

	@Autowired
	private ClientService clientService;

	@PostConstruct
	public void loadData() {
		final Faker faker = new Faker(new Locale("fr"));
		final List<Client> clients = new ArrayList<>();
		final int numberOfClients = 10;
		for (int i = 0; i < numberOfClients; i++) {
			Client client = new Client();
			client.setEntity(faker.company().name());
			client.setMail(faker.internet().emailAddress());
			client.setTel(faker.phoneNumber().cellPhone());
			// client.setPerson(PersonGenerator.generatePerson());
			clients.add(client);
		}
		clientService.addClients(clients);
	}

	@PreDestroy
	public void removeData() {
		clientService.removeAll();
	}

}
