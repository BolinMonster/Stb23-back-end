package fr.univrouen.stb23.repository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.univrouen.stb23.model.Stb;

@Repository
public interface StbRepository extends CrudRepository<Stb, Long> {

	List<Stb> findByTitleContainingIgnoreCase(String title);
	
    @Query("SELECT s FROM Stb s WHERE DATE(s.validationDate) = DATE(:validationDate)")
	List<Stb> findByValidationDate(Date validationDate);
    
    @Query("SELECT s FROM Stb s WHERE UPPER(s.title) LIKE CONCAT('%', UPPER(:title), '%') AND DATE(s.validationDate) = DATE(:validationDate)")
	List<Stb> findByTitleContainingIgnoreCaseAndValidationDate(String title, Date validationDate);
    
    @Query("SELECT s FROM Stb s WHERE UPPER(s.title) LIKE CONCAT('%', UPPER(:titre), '%') AND s.version = :version AND DATE(s.validationDate) = DATE(:validationDate)")
    Optional<Stb> findByTitreAndVersionAndValidationDate(@Param("titre") String titre, @Param("version") Double version, @Param("validationDate") Date validationDate);
    
}
