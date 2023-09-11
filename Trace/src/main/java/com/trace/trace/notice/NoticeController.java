package com.trace.trace.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trace.trace.Tokengenerator;
import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NoticeController {

	@Autowired
	Tokengenerator tg;

	@Autowired
	private MemberDAO mDAO;

	@Autowired
	private NoticeDAO nDAO;

	@RequestMapping("/notice")
	public String noticeGo(@RequestParam(name = "page", defaultValue = "1") int page, HttpServletRequest req, Member m, Notice n) {
		mDAO.islogined(req);
		nDAO.get(req, page - 1, n);
		
		req.setAttribute("cp", "notice/notices");
		req.setAttribute("cpSub", "noticeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		tg.generate(req);
		
		return "index";
	}
	
	@RequestMapping("/notice.delete")
	public String noticeDelete(Notice n, HttpServletRequest req) {
		mDAO.islogined(req);
		nDAO.delete(n, req);

		tg.generate(req);
		nDAO.get(req, 0, n);

		req.setAttribute("cp", "notice/notices");
		req.setAttribute("cpSub", "noticeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/notice.get.detail")
	public String noticeGetDetail(Notice n, NoticeCount nc, HttpServletRequest req) {
		mDAO.islogined(req);
		nDAO.getDetail2(n, nc, req);
		
		req.setAttribute("cp", "notice/detail");
		req.setAttribute("cpSub", "noticeDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		tg.generate(req);
		
		return "index";
	}
	
	@RequestMapping("/notice.search")
	public String noticeSearch(@RequestParam(name = "page", defaultValue = "1") int page, Notice n, HttpServletRequest req) {
		mDAO.islogined(req);
		nDAO.search(n, page - 1, req);

		req.setAttribute("cp", "notice/notices");
		req.setAttribute("cpSub", "noticeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}
	
	@RequestMapping("/notice.update")
	public String noticeUpdate(Notice n, NoticeCount nc, HttpServletRequest req) {
		mDAO.islogined(req);
		nDAO.update(n, req);
		
		tg.generate(req);
		nDAO.getDetail(n, nc, req);
		
		req.setAttribute("cp", "notice/detail");
		req.setAttribute("cpSub", "noticeDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		return "index";
	}
	
	@RequestMapping("/notice.update.go")
	public String noticeUpdateGo(Notice n, NoticeCount nc, HttpServletRequest req) {
		mDAO.islogined(req);
		nDAO.getDetail(n, nc, req);

		req.setAttribute("cp", "notice/update");
		req.setAttribute("cpSub", "noticeUpdate");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}
	
	@ResponseBody
	@PostMapping("/notice.like")
	public void noticeLike(NoticeCount nc, NoticeLikeCheck nlc, HttpServletRequest req) {
		nDAO.updateLikeCheck(req, nc, nlc);
		tg.generate(req);
	}

	@RequestMapping("/notice.write")
	public String noticeWrite(Notice n, NoticeCount nc, HttpServletRequest req) {
		mDAO.islogined(req);
		nDAO.write(n, nc, req);
		
		tg.generate(req);
		nDAO.get(req, 0, n);
		
		req.setAttribute("cp", "notice/notices");
		req.setAttribute("cpSub", "noticeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		return "index";
	}

	@RequestMapping("/notice.write.go")
	public String noticeWriteGo(HttpServletRequest req) {
		mDAO.islogined(req);

		req.setAttribute("cp", "notice/write");
		req.setAttribute("cpSub", "noticeWrite");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		tg.generate(req);
		return "index";
	}

}
