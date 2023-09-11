package com.trace.trace.member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLogRepo extends CrudRepository<MemberLog, Integer> {

	public abstract List<MemberLog> findAllByOrderByLogDateDesc();
	
	public abstract Page<MemberLog>findAll(Pageable p);
}
