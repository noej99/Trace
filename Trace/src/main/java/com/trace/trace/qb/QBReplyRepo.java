package com.trace.trace.qb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QBReplyRepo extends CrudRepository<QBReply, Integer> {

	public abstract List<QBReply> findByQaboard(QB qaboard);
	long countByQaboard(QB qaboard);
}
