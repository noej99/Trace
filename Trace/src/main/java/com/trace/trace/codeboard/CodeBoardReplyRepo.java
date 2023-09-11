package com.trace.trace.codeboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CodeBoardReplyRepo extends CrudRepository<CodeBoardReply, Integer> {
	
	@Query("select cbr from code_board_reply cbr where cbr.cbrCN.no = :cbNo")
	List<CodeBoardReply> findReplysByCodeBoardNO(@Param("cbNo") Integer cbNo);
}
