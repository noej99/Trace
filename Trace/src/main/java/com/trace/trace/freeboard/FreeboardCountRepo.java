package com.trace.trace.freeboard;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FreeboardCountRepo extends CrudRepository<FreeboardLike, String>{

	//해당 글번호를 매개변수로 해당 게시글의 속성들의 값을 가져온다
	public abstract List<FreeboardLike> findByNo(@Param(value="no") Integer no);
	
	@Transactional
	@Modifying
	@Query(value = "delete from freeboard_count where fbc_m_id = :id and fbc_fb_no = :no", nativeQuery=true)
	public abstract void deleteLike(@Param(value = "no") Integer no, @Param(value = "id") String id);

}
