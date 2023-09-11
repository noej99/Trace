package com.trace.trace.notice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeLikeCheckRepo extends CrudRepository<NoticeLikeCheck, Integer> {

	public abstract List<NoticeLikeCheck> findByNoAndUser(Integer no, String id);
}
