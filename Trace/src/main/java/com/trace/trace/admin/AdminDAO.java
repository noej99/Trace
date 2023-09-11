package com.trace.trace.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;
import com.trace.trace.member.MemberRepo;
import com.trace.trace.member.MemberWriterRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminDAO {
	
	@Autowired
	private MemberRepo mr;
	
	@Autowired
	private MemberWriterRepo mwr;
	
	
	
	public void setAdmin(HttpServletRequest request) {
		//@포함하지않은 아이디를 리스트로 mm에
		//그리고 그 mm을 
		Member m=(Member) request.getSession().getAttribute("loginMember");
		List<Member>mm=mr.findByIdNotContaining("@");
		for (Member member : mm) {
			if (member.getId()==m.getId()) {
				request.getSession().setAttribute("admin", mm);
			}
		
		}
		}
	public void logoutadmin(HttpServletRequest request) {
		if (request.getSession().getAttribute("admin")!=null) {
			
			request.getSession().setAttribute("admin", null);
		}
	}
	public boolean isloginedAdmin(HttpServletRequest request) {
		Member m = (Member) request.getSession().getAttribute("admin");
		if (m != null) {
			
			return true;
		} else {
			return false;
		}

	}
	
	
	public void getAllMember(HttpServletRequest request) {
		List<Member>m=mr.findAll();
		request.setAttribute("m", m);
		
	}
	
	public void deleteWriterandMember(HttpServletRequest request) {
		String id= request.getParameter("id");
		Member m=mr.findById(id).get();
		m.setLeave(1);
		if (mr.save(m)!=null) {
			mwr.deleteById(id);
			
		}
		
	}
	public void deleteMember(HttpServletRequest request) {
		String id= request.getParameter("id");
		Member m=mr.findById(id).get();
		m.setLeave(1);
		mr.save(m); 
			
		
		
	}
	
	
}
	
	
	
	
	

		
		
	
		


