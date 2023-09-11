package com.trace.trace.notice;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepo extends CrudRepository<Notice, Integer> {

	public abstract List<Notice> findAll();
	public abstract List<Notice> findByTitleContaining(String s);
	public abstract List<Notice> findByTitleContaining(String s, Pageable p);
	public abstract List<Notice> findByContentsContaining(String s);
	public abstract List<Notice> findByContentsContaining(String s, Pageable p);
	public abstract List<Notice> findByCategoryAndTitleContaining(String category, String title, Pageable p);
	
	public abstract List<Notice> findByNoEquals(Integer no);
	public abstract List<Notice> findByCategoryEquals(String s, Pageable p);
	
	public abstract List<Notice> deleteByNo(Integer no);
}
