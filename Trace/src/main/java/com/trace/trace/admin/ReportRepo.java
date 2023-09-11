package com.trace.trace.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo  extends CrudRepository<Report, Integer>{
	public abstract Page<Report> findAll(Pageable p);
}
