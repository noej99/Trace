package com.trace.trace.chat;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;
import com.trace.trace.member.Members;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ChatController {

	@Autowired
	private MemberDAO mDAO;
	
	
	
	@RequestMapping("/go.chat")
	public String gochat(HttpServletRequest request){
		if (mDAO.islogined(request)) {
			request.setAttribute("cp", "chat/chat");
			request.setAttribute("cpSub", "chatItem");
			request.setAttribute("loginPage", "member/logined");
			request.setAttribute("loginSub", "loginedM");
		}else {

			
			return "member/login";
			
		}
		
		
		
		return "index";
		
	}
	@RequestMapping("member.get.json2")
	public @ResponseBody Members getmemberjson(HttpServletRequest request, Member m, HttpServletResponse res) {
		res.addHeader("Access-Control-Allow-Origin", "*");
		Members ms = mDAO.getMemberIDNickJson(m,request);
		return ms;
	}
	
	
	
	
	
}
