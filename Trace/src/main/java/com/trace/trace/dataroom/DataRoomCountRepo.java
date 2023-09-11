package com.trace.trace.dataroom;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRoomCountRepo extends CrudRepository<DataRoomCount, Integer> {
	public abstract DataRoomCount findByNo(Integer n);
}
