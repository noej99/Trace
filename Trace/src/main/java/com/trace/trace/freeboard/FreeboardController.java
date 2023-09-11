package com.trace.trace.freeboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trace.trace.Tokengenerator;
import com.trace.trace.freeboard.reply.FB_ReplyDAO;
import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;
import com.trace.trace.notice.Notice;
import com.trace.trace.notice.NoticeDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FreeboardController {

	@Autowired
	private Tokengenerator tGT;
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private FreeboardDAO fDAO;
	
	@Autowired
	private FB_ReplyDAO fbrDAO;
	
	@Autowired
	private NoticeDAO nDAO;
	
	@RequestMapping("freeboard")
	public String goFreeboard(HttpServletRequest request, Member m, Pageable p,
			@RequestParam(name="category", defaultValue = "freeboard")String thisPage, Notice N) {
		mDAO.islogined(request);
		mDAO.login(request, m);
		nDAO.getNoticeByCategory(request, thisPage);
		nDAO.getCount(N, request);
		request.setAttribute("cp", "freeboard/freeboard"); //콘텐츠페이지
		request.setAttribute("cpSub", "");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		tGT.generate(request);
//		Member curMem = (Member)request.getSession().getAttribute("loginMember"); //현재 세션의 아이디,비번
//		System.out.println(curMem.getId()+" : "+curMem.getPw());
		fDAO.get(request);
		return "index";
	}
	
	@RequestMapping("freeboard.go.write") //글을 쓰러가는 Url
	public String gowriteFreeboard(HttpServletRequest request, Member m, Pageable p,
			@RequestParam(name="category", defaultValue = "freeboard")String thisPage, Notice N) {
		mDAO.login(request, m);
		if(mDAO.islogined(request)) { //로그인 했으면 글을 작성하는 페이지로 이동가능
			request.setAttribute("cp", "freeboard/write_FreeBoard");
			request.setAttribute("cpSub", "freeboardWrite");
		}else { // 로그인하지 않았으면 게시판으로 빠꾸
			request.setAttribute("cp", "freeboard/freeboard");
			fDAO.get(request);
			nDAO.getNoticeByCategory(request, thisPage);
			nDAO.getCount(N, request);
		}
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		tGT.generate(request);
		return "index";
	}
	
	//
	@PostMapping("freeboard.write")
	public String writeFreeboard(HttpServletRequest request, Freeboard f, Member m, Pageable p,
			@RequestParam(name="category", defaultValue = "freeboard")String thisPage, Notice N) {
		mDAO.login(request, m);
		if(mDAO.islogined(request)) { // go.write에서 거르긴 하지만, 글 작성 html에서 너무 오래 머물러 세션이 만료되거나 했을 때..
			fDAO.write(request,f); // 글작성 write
		}
		request.setAttribute("cp", "freeboard/freeboard");
		request.setAttribute("cpSub", "");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		tGT.generate(request);
		nDAO.getNoticeByCategory(request, thisPage);
		nDAO.getCount(N, request);
		fDAO.get(request); // 글 작성후 글 목록 로딩
		return "index";
	}
	
	@RequestMapping("freeboard.post") // 게시글 상세보기
	public String readFreeboard(HttpServletRequest request, Freeboard f, Member m) {
		mDAO.islogined(request);
		mDAO.login(request, m);
		request.setAttribute("cp", "freeboard/readFreeBoard");
		request.setAttribute("cpSub", "");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		fDAO.postLikeGet(request);
		fDAO.postGet(request);
		fbrDAO.get_reply(request);
		
		tGT.generate(request);
		return "index";
	}
	
	@RequestMapping("freeboard.del")
	public String deleteFreeboard(HttpServletRequest request, Freeboard f, Member m, Pageable p,
			@RequestParam(name="category", defaultValue = "freeboard")String thisPage, Notice N) {
		mDAO.login(request, m);
		
		if(mDAO.islogined(request)) {
			fDAO.postDel(request);
		}else { 
			System.out.println("삭제 실패 : 세션 만료 혹은 작성자가 아님");
		}
		request.setAttribute("cp", "freeboard/freeboard");
		request.setAttribute("cpSub", "");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		nDAO.getNoticeByCategory(request, thisPage);
		nDAO.getCount(N, request);
		fDAO.get(request);
		
		tGT.generate(request);
		return "index";
	}
	
	@RequestMapping("freeboard.goupdate") // 업데이트 html로 이동하기
	public String goupdateFreeboard(HttpServletRequest request,Member m, Pageable p,
			@RequestParam(name="category", defaultValue = "freeboard")String thisPage, Notice N) {
		mDAO.login(request, m);
		if(mDAO.islogined(request)) {
			request.setAttribute("cp", "freeboard/updateFreeBoard");	
		}else { // 로그인하지 않고 Url로 업데이트 시도시,  게시판으로 빠꾸.
			// 해당 버튼은 애초에 사용자만 볼 수 있게 해두었음
			request.setAttribute("cp", "freeboard/freeboard");
			nDAO.getNoticeByCategory(request, thisPage);
			nDAO.getCount(N, request);
			fDAO.get(request);
		}
		request.setAttribute("cpSub", "freeboardUpdate");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		
		fDAO.postGet(request);
		
		
		tGT.generate(request);
		return "index";
	}
	
	// contents 내용이 길어질수록 url을 많이 차지하기 때문에, Post 방식으로 이동하게 해야함
	@PostMapping("freeboard.update") //updateFreeboard,html에서 수정한 내용을 DB에 반영하고 해당 글 상세보기로 이동
	public String updateFreeboard(HttpServletRequest request, Freeboard f,Member m, Pageable p,
			@RequestParam(name="category", defaultValue = "freeboard")String thisPage, Notice N) {
		mDAO.login(request, m);
		if(mDAO.islogined(request)) {
			fDAO.postUpdate(request, f);
			request.setAttribute("cp", "freeboard/readFreeBoard");
			
			fDAO.postGet(request);
			fbrDAO.get_reply(request);
			
		}else { //세션 만료 등의 사유로 수정 중 로그인 해제시 게시판으로 이동.
			// 해당 버튼은 애초에 사용자만 볼 수 있게 해두었음
			request.setAttribute("cp", "freeboard/freeboard");
			nDAO.getNoticeByCategory(request, thisPage);
			nDAO.getCount(N, request);
			fDAO.postLikeGet(request);
			fDAO.get(request);
		}
		request.setAttribute("cpSub", "");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		
		tGT.generate(request);
		return "index";
	}
	
	@RequestMapping(value="freeboard.like")
	public String likeFreeboard(HttpServletRequest request,Member m) {
		mDAO.login(request, m);
		if(mDAO.islogined(request)){ // 로그인 상태일 때만, 좋아요 기능 수행
			fDAO.postLike(request); // 애초에 로그인 하지 않을 상태에서는 좋아요 버튼이 보이지 않는다
		}
		request.setAttribute("cp", "freeboard/readFreeBoard");
		request.setAttribute("cpSub", "");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		

		fDAO.postLikeGet(request);
		fDAO.postGet(request);
		fbrDAO.get_reply(request);
		tGT.generate(request);
		return "index";
	}
}
