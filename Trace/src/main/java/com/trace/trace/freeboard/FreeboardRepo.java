package com.trace.trace.freeboard;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FreeboardRepo  extends CrudRepository<Freeboard, String>{
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	
	public abstract List<Freeboard> findAll();
	public abstract List<Freeboard> findAllByOrderByDateDesc(Pageable p);
	public abstract Freeboard findByNo(Integer no); 
	//DB의 속성명을 규칙에 맞게 작성!! findByNo -> 찾는다 ~으로 DB의 속성명 no를 기준으로
	
	//게시글 검색 및 페이징
	//Containing을 통해 받은 검색어가 포함된 게시글 리스트를 반환한다 Like 연산
	public abstract Page<Freeboard> findByContentsContaining(String search, Pageable p);
	
	
	@Transactional // UPDATE, DELETE 문에서 해당 어노테이션 명시해야 작동
	@Modifying //SELECT가 아님을 명시
    @Query(value="update freeboard f set f.fb_view = f.fb_view + 1 where f.fb_no = :no", nativeQuery=true)
    public abstract void updateView(@Param(value = "no") Integer no);
	
//	@Query("update freeboard f set f.fb_contents = :contents where f.no = :no")
//	public abstract void updateFreeboard(@Param(value = "no") Integer no, @Param(value="contents")String contents);
	
	
	
}
