package com.trace.trace.member;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MemberRepo extends CrudRepository<Member, String> {
	
	
	
	public abstract List<Member>findAll();
	public abstract List<Member>findByIdEquals(String id);
	public abstract List<Member>findByPwEquals(String pw);
	public abstract List<Member>findByIdEqualsAndLeave(String id,Integer leave);
	public abstract List<Member>findByIdNotContaining(String gol);
	
	
	
}
