package com.trace.trace.qb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QBLikeCheckRepo extends CrudRepository<QBLikeCheck, Integer> {

	public abstract List<QBLikeCheck> findByQlcIdAndQlcno(String id, int no);
}
