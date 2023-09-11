package com.trace.trace.dataroom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRoomImageRepo extends CrudRepository<DataRoomImage, Integer> {
	public abstract List<DataRoomImage> findAll();

	public abstract List<DataRoomImage> findByDrNo(Integer no);
}
