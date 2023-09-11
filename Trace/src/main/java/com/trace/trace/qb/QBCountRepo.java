package com.trace.trace.qb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QBCountRepo extends CrudRepository<QBCount, Integer> {

	public abstract List<QBCount> findAll();
	public abstract List<QBCount> findByNo(Integer no);
}
