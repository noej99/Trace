package com.trace.trace.onetoneboard;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnetoOneRepo extends CrudRepository<OnetoOneBoard, Integer> {
	public abstract List<OnetoOneBoard> findByNoEquals(Integer no);

}
