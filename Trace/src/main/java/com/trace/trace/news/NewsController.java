package com.trace.trace.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trace.trace.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class NewsController {

	@Autowired
	private MemberDAO mDAO;
	
	@RequestMapping("/go.news")
	public String getITNews(HttpServletRequest request) {
		mDAO.islogined(request);
		request.setAttribute("cp", "news/news");
		request.setAttribute("cpSub", "newsItem");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	@RequestMapping("/go.world.news")
	public String getWorldNews(HttpServletRequest request) {
		mDAO.islogined(request);
		request.setAttribute("cp", "news/newsworld");
		request.setAttribute("cpSub", "newsItem");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	@RequestMapping("/go.culture.news")
	public String getCultureNews(HttpServletRequest request) {
		mDAO.islogined(request);
		request.setAttribute("cp", "news/newsculture");
		request.setAttribute("cpSub", "newsItem");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	@RequestMapping("/go.itTop10")
	public String getitTop10(HttpServletRequest request) {
		return "news/ittop10";
		
	}
	@RequestMapping("/go.cultureTop10")
	public String getCultureTop10(HttpServletRequest request) {
		return "news/culturetop10";
		
	}
	@RequestMapping("/go.worldTop10")
	public String getWorldTop10(HttpServletRequest request) {
		return "news/worldtop10";
		
	}
	
	
}
