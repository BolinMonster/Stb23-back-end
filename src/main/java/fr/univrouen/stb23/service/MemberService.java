package fr.univrouen.stb23.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univrouen.stb23.model.Member;
import fr.univrouen.stb23.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	public Iterable<Member> getMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> getMemberById(Long id) {
		return memberRepository.findById(id);
	}

	public Member addMember(Member member) {
		return memberRepository.save(member);
	}
	
	public Iterable<Member> addMembers(Iterable<Member> members) {
		return memberRepository.saveAll(members);
	}
	
	public void removeMember(Member member) {
		memberRepository.delete(member);
	}
	
	public void removeMemberById(Long id) {
		memberRepository.deleteById(id);
	}
	
	public void removeAll() {
		memberRepository.deleteAll();
	}

}
