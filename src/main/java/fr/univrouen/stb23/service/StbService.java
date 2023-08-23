package fr.univrouen.stb23.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import fr.univrouen.stb23.model.Stb;
import fr.univrouen.stb23.repository.StbRepository;

@Service
public class StbService {
	
	@Autowired
	private StbRepository stbRepository;

	public Iterable<Stb> getStbs() {
		return stbRepository.findAll();
	}
	
	public long getNumberOfStb() {
		return stbRepository.count();
	}

	public Optional<Stb> getStbById(Long id) {
		return stbRepository.findById(id);
	}
	
    public List<Stb> getStbByTitleSimilarTo(String title) {
        return stbRepository.findByTitleContainingIgnoreCase(title);
    }
	
    public List<Stb> getStbByValidationDate(Date validationDate) {
        return stbRepository.findByValidationDate(validationDate);
    }
    
    public List<Stb> getStbByTitleSimilarToAndValidationDate(String title, Date validationDate) {
        return stbRepository.findByTitleContainingIgnoreCaseAndValidationDate(title, validationDate);
    }
    
    public Optional<Stb> getStbByTitleSimilarToAndValidationDateAndVersion(String titre, Double version, Date validationDate) {
    	return stbRepository.findByTitreAndVersionAndValidationDate(titre, version, validationDate);
    }

	public Stb addStb(Stb stb) {
		return stbRepository.save(stb);
	}
	
	public Iterable<Stb> addStbs(Iterable<Stb> stbs) {
		return stbRepository.saveAll(stbs);
	}
	
	public void removeStb(Stb stb) {
		stbRepository.delete(stb);
	}
	
	public void removeStbById(Long id) {
		stbRepository.deleteById(id);
	}
	
	public void removeAll() {
		stbRepository.deleteAll();
	}

}
