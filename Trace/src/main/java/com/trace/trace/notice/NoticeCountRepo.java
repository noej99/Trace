package com.trace.trace.notice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeCountRepo extends CrudRepository<NoticeCount, Integer> {
	
	public abstract List<NoticeCount> findAll();
	public abstract List<NoticeCount> findByNo(Integer no);
	
}
