package com.trace.trace.dataroom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DataRoomReplyRepo extends CrudRepository<DataRoomReply, Integer>{
	
	@Query("SELECT drr FROM DATAROOM_REPLY drr WHERE drr.drrDr.no = :drNo")
	List<DataRoomReply> findRepliesByDataRoomNo(@Param("drNo") Integer drNo);
}