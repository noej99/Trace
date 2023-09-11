package com.trace.trace.freeboard.reply;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FB_ReplyDAO {
	
	@Autowired
	private FB_ReplyRepo fbrr;
	
	@Autowired
	private FB_ReReplyRepo fbrrr;
	
	public void write_reply(HttpServletRequest req, FB_Reply fbr) {
		String token = req.getParameter("token"); // form 에서 토큰 받아오기
		String lastToken = (String) req.getSession().getAttribute("lastSuccessToken");
		
		if (lastToken != null && token.equals(lastToken)) {
			System.out.println("Token : "+token+" Last : "+lastToken);
			// 댓글쓰기 성공한 적이 없거나( 글 작성한 적 없음) or 현재토큰과 가져온 토큰(마지막 글쓰기 성공)이 같다면
			return;
		}
		
		Member m = (Member) req.getSession().getAttribute("loginMember");
		fbr.setWriter(m.getNick());
		fbr.setDate(new Date());
		
		fbrr.save(fbr);
		req.getSession().setAttribute("lastSuccessToken", token);
		// 글쓰기 성공시 마지막 성공 토큰을 세션으로 보낸다.
		System.out.println("댓 작성완료");
		
	}
	
	public void get_reply(HttpServletRequest req) {
		List<FB_Reply> replys = fbrr.findByNoOrderByDate(Integer.parseInt(req.getParameter("no")));
		//게시글을 클릭할때, 딸려오는 파라미터 no를 이용하여 게시글 번호를 넣어 리스트를 반환 받는다.
		//System.out.println("들어온 댓글 번호 : "+replys.toString());
		//답댓글 또한 가져오기
//		List<FB_ReplyRe> re_replys =fbrrr.findByNoOrderByDate(Integer.parseInt(req.getParameter("no")));
		// 테이블 조인으로 해결
		
		req.setAttribute("replys", replys);
//		req.setAttribute("re_replys", re_replys);
	}
	
	public void delete_reply(HttpServletRequest req) {
		fbrr.deleteById(req.getParameter("r_no")); //JavaScript에서 location.href로 같이 전달한 댓글 번호 파라미터를 받아 전달
	}
	
	public void delete_re_reply(HttpServletRequest req) {
		fbrrr.deleteById(req.getParameter("fbrr_no"));
	}
	
	public void write_ReReply(HttpServletRequest req, FB_ReplyRe rr) {
		String token = req.getParameter("token"); // form 에서 토큰 받아오기
		String lastToken = (String) req.getSession().getAttribute("lastSuccessToken");
		
		if (lastToken != null && token.equals(lastToken)) {
			System.out.println("Token : "+token+" Last : "+lastToken);
			// 댓글쓰기 성공한 적이 없거나( 글 작성한 적 없음) or 현재토큰과 가져온 토큰(마지막 글쓰기 성공)이 같다면
			return;
		}
		
		Member m = (Member) req.getSession().getAttribute("loginMember");
		rr.setWriter(m.getNick());
		rr.setDate(new Date());
		
		fbrrr.save(rr);
		req.getSession().setAttribute("lastSuccessToken", token);
		// 대댓글쓰기 성공시 마지막 성공 토큰을 세션으로 보낸다.
		System.out.println("대댓 작성완료");
	}
	
	

}
