package com.trace.trace.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private AdminDAO aDAO;
	
	@Autowired
	private ReportDAO rDAO;
	
	@RequestMapping("/admin.go")
	public String adminGo(HttpServletRequest request,Member m) {
		if (mDAO.islogined(request)) {
			rDAO.getReport(request);
			aDAO.getAllMember(request);
			mDAO.getLog(request);
			return "admin/adminview";
			
  		}else {
  		return"/error";
		}
		
		}
	
	@RequestMapping("/adimin.delete.writeandmember")
	public String adminDeleteWriteMember(HttpServletRequest request) {
		if (mDAO.islogined(request)) {
			rDAO.getReport(request);
			aDAO.deleteWriterandMember(request);
			aDAO.getAllMember(request);
			mDAO.getLog(request);
			return "admin/adminview";
			
		} else {
			return "/error";
		}
		
	}
	@RequestMapping("/adimin.delete.member")
	public String adminDeleteMember(HttpServletRequest request) {
		if (mDAO.islogined(request)) {
			rDAO.getReport(request);
			aDAO.deleteMember(request);
			aDAO.getAllMember(request);
			mDAO.getLog(request);
			return "admin/adminview";
			
		} else {
			return"/error";
		}
	}
	
	
	
}
	

