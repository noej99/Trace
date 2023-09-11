package com.trace.trace.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;
import com.trace.trace.member.MemberRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ChatDAO {

	@Autowired
	private MemberRepo mr;
	
	
	public void getUserName(HttpServletRequest request) {
		Member m=(Member) request.getSession().getAttribute("loginMember");
		if (m!=null) {
			
			
		}
	}
	
}
