package com.trace.trace.codeboard;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.trace.trace.member.MemberWriter;


@Repository
public interface CodeBoardRepo extends CrudRepository<CodeBoard, Integer> {
	public abstract	List<CodeBoard> findAll();
	public abstract List<CodeBoard> findByTitleContaining(String s);
	public abstract List<CodeBoard> findByTitleContaining(String s, Pageable p);
	public abstract List<CodeBoard> findByTxtContaining(String s);
	public abstract List<CodeBoard> findByTxtContaining(String s, Pageable p);
	
	public abstract List<CodeBoard> findByNoEquals(Integer no);
	public abstract List<CodeBoard> deleteByNo(Integer no);
}
