package fr.univrouen.stb23.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univrouen.stb23.model.Client;
import fr.univrouen.stb23.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	public Iterable<Client> getClients() {
		return clientRepository.findAll();
	}
	
	public long getNumberOfClient() {
		return clientRepository.count();
	}

	public Optional<Client> getClientById(Long id) {
		return clientRepository.findById(id);
	}

	public Client addClient(Client client) {
		return clientRepository.save(client);
	}
	
	public Iterable<Client> addClients(Iterable<Client> clients) {
		return clientRepository.saveAll(clients);
	}
	
	public void removeStb(Client client) {
		clientRepository.delete(client);
	}
	
	public void removeStbById(Long id) {
		clientRepository.deleteById(id);
	}
	
	public void removeAll() {
		clientRepository.deleteAll();
	}

}
