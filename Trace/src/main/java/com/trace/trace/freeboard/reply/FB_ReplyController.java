package com.trace.trace.freeboard.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trace.trace.Tokengenerator;
import com.trace.trace.freeboard.Freeboard;
import com.trace.trace.freeboard.FreeboardDAO;
import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FB_ReplyController {
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private FreeboardDAO fDAO;
	
	@Autowired
	private FB_ReplyDAO fbrDAO;
	
	@Autowired
	private Tokengenerator tGT;
	
	
	
	@PostMapping("freeboard.reply.write")
	public String writeReply( FB_Reply r,Member m, 
			HttpServletRequest req, Freeboard f) {
		mDAO.login(req, m);
		if(mDAO.islogined(req)) {
			fbrDAO.write_reply(req, r); // 댓글쓰기 DAO 메소드
		}else { // 애초에 HTML에서 미로그인시. 댓글을 작성하는 란, 그리고 작성 버튼이 보이지 않음. 그러나 Url로 들어오는 것을 방지
			System.out.println("댓글 작성 실패: 로그인이 되지 않은 상태이거나 세션 만료");
		}
		req.setAttribute("cp", "freeboard/readFreeBoard");
		req.setAttribute("cpSub", "");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		tGT.generate(req);
		
		
		fDAO.postGet(req); //해당 게시글 불러오기
		fDAO.postLikeGet(req);
		fbrDAO.get_reply(req); //해당 게시글의 댓글 리스트를 불러온다
		return "index";
	}
	
	@RequestMapping("freeboard.reply.del")
	public String delReply(HttpServletRequest req, Member m) {
		mDAO.login(req, m);
		if(mDAO.islogined(req)) { // 해당 버튼도 비로그인시 보이지 않음, 작성자가 아닐시
			fbrDAO.delete_reply(req);
			
		}else {
			System.out.println("댓글 삭제 실패 : 세션이 만료되었거나, 작성자가 아님");
		}
		req.setAttribute("cp", "freeboard/readFreeBoard");
		req.setAttribute("cpSub", "");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		
		fDAO.postGet(req); //해당 게시글 불러오기
		
		fbrDAO.get_reply(req); //해당 게시글의 댓글 리스트를 불러온다
		tGT.generate(req);
		return "index";
	}
	
	@PostMapping("freeboard.rereply.write")
	public String writeReReply(HttpServletRequest req, Member m,
			Freeboard f, FB_ReplyRe rr) {
		mDAO.login(req, m);
		if(mDAO.islogined(req)) { // 로그인한 것이 맞다면
			fbrDAO.write_ReReply(req, rr); // 대댓글쓰기 DAO 메소드
			
		}// 사실 로그인 안한 사용자들은 대댓글 작성 및 버튼이 보이지 않음
		req.setAttribute("cp", "freeboard/readFreeBoard");
		req.setAttribute("cpSub", "");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		tGT.generate(req);
		
		
		fDAO.postGet(req); //해당 게시글 불러오기
		fDAO.postLikeGet(req);
		fbrDAO.get_reply(req); //해당 게시글의 댓글 리스트를 불러온다
		return "index";
	}
	
	@RequestMapping("freeboard.re_reply.del")
	public String delReReply(HttpServletRequest req, Member m) {
		mDAO.login(req, m);
		if(mDAO.islogined(req)) {
			fbrDAO.delete_re_reply(req);
			
		}
		req.setAttribute("cp", "freeboard/readFreeBoard");
		req.setAttribute("cpSub", "");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		
		fDAO.postGet(req); //해당 게시글 불러오기
		
		fbrDAO.get_reply(req); //해당 게시글의 댓글 리스트를 불러온다
		tGT.generate(req);
		return "index";
	}
	

}
