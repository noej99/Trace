package com.trace.trace;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class Tokengenerator {
	
	private SimpleDateFormat sdf;
	
	public Tokengenerator() {
		sdf=new SimpleDateFormat("YYYYMMddHHmmss");
		// TODO Auto-generated constructor stub
	} 
	public void generate(HttpServletRequest request) {
		Date now = new Date();
		String token = sdf.format(now);
		request.setAttribute("token", token);
	}

}
