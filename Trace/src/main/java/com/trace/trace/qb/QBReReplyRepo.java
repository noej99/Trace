package com.trace.trace.qb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QBReReplyRepo extends CrudRepository<QBReReply, Integer> {

	public abstract List<QBReReply> findAll();
}
