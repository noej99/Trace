package com.trace.trace.dataroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRoomRepo extends CrudRepository<DataRoom, Integer> {
	public abstract Page<DataRoom> findAll(Pageable p);
	public abstract DataRoom findByNo(Integer n);
	public abstract Page<DataRoom> findByTitleContaining(String s, Pageable p);
	public abstract Page<DataRoom> findByContentContaining(String s, Pageable p);
	public abstract Page<DataRoom> findByDrMwContaining(String s, Pageable p);
}
