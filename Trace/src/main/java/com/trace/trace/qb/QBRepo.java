package com.trace.trace.qb;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QBRepo extends CrudRepository<QB, Integer> {
	public abstract Page<QB> findAll(Pageable p);
	public abstract List<QB> findByNoEquals(Integer n);
	public abstract Page<QB> findByTitleContaining(String s, Pageable p);
	public abstract Page<QB> findByCateContaining(String c, Pageable p);
	public abstract Page<QB> findByCateOrTitleContaining(String c, String s, Pageable p);
	public abstract Page<QB> findByCateAndTitleContaining(String c, String s, Pageable p);
}
