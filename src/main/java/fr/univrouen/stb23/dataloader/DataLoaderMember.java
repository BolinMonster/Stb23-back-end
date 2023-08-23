package fr.univrouen.stb23.dataloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.univrouen.stb23.model.Member;
import fr.univrouen.stb23.service.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataLoaderMember {
	
	@Autowired
	private MemberService memberService;
	
	@PostConstruct
    public void loadData() {
        final Faker faker = new Faker(new Locale("fr"));
        final List<Member> members = new ArrayList<>();
        final int numberOfMembers = 10;
        for (int i = 0; i < numberOfMembers; i++) {
            Member member = new Member();
            member.setMail(faker.internet().emailAddress());
            member.setFunction(faker.job().title());
            // member.setPersonne(new Person(faker.name().firstName(), faker.name().lastName()));
            members.add(member);
        }
        memberService.addMembers(members);
    }
	
    @PreDestroy
    public void removeData() {
    	memberService.removeAll();
    }

}
