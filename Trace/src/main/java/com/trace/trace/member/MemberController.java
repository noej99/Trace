package com.trace.trace.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trace.trace.Tokengenerator;
import com.trace.trace.admin.AdminDAO;
import com.trace.trace.codeboard.CodeBoard;
import com.trace.trace.codeboard.CodeBoardDAO;
import com.trace.trace.dataroom.DataRoomDAO;
import com.trace.trace.freeboard.FreeboardDAO;
import com.trace.trace.notice.Notice;
import com.trace.trace.notice.NoticeDAO;
import com.trace.trace.qb.QBDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private Tokengenerator tGT;

	@Autowired
	private AdminDAO aDAO;
	@Autowired
	private NoticeDAO nDAO;
	@Autowired
	private DataRoomDAO drDAO;
	@Autowired
	private QBDAO qDAO;
	@Autowired
	private FreeboardDAO fDAO;
	@Autowired
	private CodeBoardDAO cDAO;

	@RequestMapping("member.get.json")
	public @ResponseBody Members getmemberjson(HttpServletRequest request, Member m, HttpServletResponse res) {
		res.addHeader("Access-Control-Allow-Origin", "*");
		Members ms = mDAO.getMemberJson(m);
		return ms;
	}

	@RequestMapping("member.join.go")
	public String memberJoingo(HttpServletRequest request) {
		return "member/join";
	}

	@RequestMapping("member.findpw.go")
	public String memberFindPwGo(HttpServletRequest request) {
		return "member/findpw";
	}

	@RequestMapping("login.go")
	public String memberLogingo(HttpServletRequest request, Member m) {

		mDAO.login(request, m);
		tGT.generate(request);
		return "member/login";

	}

	@RequestMapping("member.join")
	public String memberJoin(HttpServletRequest request, Member m, MemberWriter mw, Notice n,CodeBoard cd) {
		mDAO.islogined(request);
		mDAO.login(request, m);
		nDAO.get(request, 0, n);
		cDAO.get(request, 0, cd);
		drDAO.get(request);
		qDAO.get(request);
		fDAO.get(request);
		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		tGT.generate(request);
		mDAO.join(request, m, mw);
		return "index";
	}

	@RequestMapping("member.detail")
	public String memberDetail(HttpServletRequest request, Member m, MemberWriter mw, Notice n,CodeBoard cd) {
		mDAO.islogined(request);
		mDAO.login(request, m);
		nDAO.get(request, 0, n);
		cDAO.get(request, 0, cd);
		drDAO.get(request);
		qDAO.get(request);
		fDAO.get(request);

		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		tGT.generate(request);
		mDAO.join(request, m, mw);
		return "index";
	}

	@RequestMapping("member.detailck.go")
	public String memberDetailck(HttpServletRequest request, Member m, MemberWriter mw) {
		request.setAttribute("cp", "member/detailck");
		request.setAttribute("cpSub", "re");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		mDAO.islogined(request);
		mDAO.login(request, m);
		tGT.generate(request);
		return "index";
	}

	@RequestMapping("member.logout")
	public String memberLogout(HttpServletRequest request, Notice n,CodeBoard cd) {
		mDAO.logout(request);
		aDAO.logoutadmin(request);
		nDAO.get(request, 0, n);
		cDAO.get(request, 0, cd);
		drDAO.get(request);
		qDAO.get(request);
		fDAO.get(request);

		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");

		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";

	}

	@RequestMapping("member.login")
	public String memberLogin(HttpServletRequest request, Member m, Notice n,CodeBoard cd) {
		mDAO.login(request, m);
		if (mDAO.islogined(request)) {
			aDAO.setAdmin(request);
			cDAO.get(request, 0, cd);
			nDAO.get(request, 0, n);
			drDAO.get(request);
			qDAO.get(request);
			fDAO.get(request);
			request.setAttribute("cp", "home");
			request.setAttribute("cpSub", "homeboard");
			
			request.setAttribute("loginPage", "member/logined");
			request.setAttribute("loginSub", "loginedM");
			return "index";
			
		}else {
			mDAO.login(request, m);
			
			tGT.generate(request);
			return "member/loginfailed";
		}
		
			
			
			
		
	}

	@RequestMapping("member.detail.go")
	public String memberDetail(HttpServletRequest request, Member m, Notice n,CodeBoard cd) {
		if (mDAO.islogined(request)&&mDAO.loginCK(request)) {
			mDAO.login(request, m);
			mDAO.splitAddr(request);
			mDAO.splitbirth(request);
			request.setAttribute("loginPage", "member/logined");
			request.setAttribute("loginSub", "loginedM");
			request.setAttribute("cp", "member/detail");
			request.setAttribute("cpSub", "memberdetail");
			return "index";

		} else {
			nDAO.get(request, 0, n);
			cDAO.get(request, 0, cd);
			drDAO.get(request);
			qDAO.get(request);
			fDAO.get(request);

			request.setAttribute("cp", "home");
			request.setAttribute("cpSub", "homeboard");
			request.setAttribute("loginPage", "member/logined");
			request.setAttribute("loginSub", "loginedM");
			return "index";
		}
	}

	@RequestMapping("member.detail.update")
	public String memberUpdateDetail(HttpServletRequest request, Member m, MemberWriter mw,CodeBoard cd) {
		mDAO.islogined(request);
		mDAO.login(request, m);
		mDAO.update(request, m, mw);
		cDAO.get(request, 0, cd);
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		return "index";
	}

	@RequestMapping("member.delete")
	public String memberdelete(HttpServletRequest request, Member m, MemberWriter mw) {
		mDAO.islogined(request);
		mDAO.login(request, m);
		mDAO.delete(request);
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		return "index";
	}

	@RequestMapping("member.findedpw")
	public String memberFindedPW(HttpServletRequest request, Member m, MemberWriter mw, Notice n,CodeBoard cd) {

		mDAO.islogined(request);
		mDAO.login(request, m);
		mDAO.updatePw(request, m);
		nDAO.get(request, 0, n);
		cDAO.get(request, 0, cd);
		drDAO.get(request);
		qDAO.get(request);
		fDAO.get(request);

		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		return "index";
	}

}
