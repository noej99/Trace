package com.trace.trace.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReportDAO {

	@Autowired
	private ReportRepo rr;
	
	public void doReport(HttpServletRequest request, Report rp) {
		try {
			rp.setCate(request.getParameter("cate"));
			rp.setCate2(request.getParameter("cate2"));
			
			rr.save(rp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void  getReport(HttpServletRequest request) {
		try {
			Sort s = Sort.by(Sort.Order.desc("no"));
	        Pageable p = PageRequest.of(0, 20, s);
			Page<Report>rrs=rr.findAll(p);
			request.setAttribute("r", rrs);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
