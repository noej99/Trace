package com.trace.trace;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trace.trace.admin.AdminDAO;
import com.trace.trace.codeboard.CodeBoard;
import com.trace.trace.codeboard.CodeBoardDAO;
import com.trace.trace.dataroom.DataRoomDAO;
import com.trace.trace.freeboard.FreeboardDAO;
import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;
import com.trace.trace.notice.Notice;
import com.trace.trace.notice.NoticeDAO;
import com.trace.trace.qb.QBDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@Autowired
	private Tokengenerator tGT;
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private AdminDAO aDAO;

	@Autowired
	private QBDAO qDAO;

	@Autowired
	private NoticeDAO nDAO;

	@Autowired
	private DataRoomDAO drDAO;
	
	@Autowired
	private FreeboardDAO fDAO;
	
	@Autowired
	private CodeBoardDAO cDAO;

	@RequestMapping("/")
	public String home(HttpServletRequest request, Member m, Notice n,CodeBoard cd) {
		nDAO.get(request, 0, n);
		drDAO.get(request);
		qDAO.get(request);
		fDAO.get(request);
		cDAO.get(request, 0, cd);
		mDAO.islogined(request);
		mDAO.login(request, m);

		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");

		tGT.generate(request);
		return "index";
	}

}
