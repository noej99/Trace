package com.trace.trace.dataroom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DataRoomFileRepo extends CrudRepository<DataRoomFile, Integer>{
	public abstract List<DataRoomFile> findAll();
	public abstract List<DataRoomFile> findByDrNo(Integer no);
}
