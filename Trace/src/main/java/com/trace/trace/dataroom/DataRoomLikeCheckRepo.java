package com.trace.trace.dataroom;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRoomLikeCheckRepo extends CrudRepository<DataRoomLikeCheck, Integer> {
	public abstract boolean existsByDrNoAndLcId(Integer no, String id);
}
