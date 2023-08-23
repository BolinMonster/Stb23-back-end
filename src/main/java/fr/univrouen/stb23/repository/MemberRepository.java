package fr.univrouen.stb23.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import fr.univrouen.stb23.model.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
	
}
