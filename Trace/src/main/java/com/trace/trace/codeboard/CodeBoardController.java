package com.trace.trace.codeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trace.trace.Tokengenerator;
import com.trace.trace.member.Member;
import com.trace.trace.member.MemberDAO;
import com.trace.trace.notice.Notice;
import com.trace.trace.notice.NoticeDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CodeBoardController {

	@Autowired
	private Tokengenerator tg;

	@Autowired
	private MemberDAO mDAO;

	@Autowired
	private CodeBoardDAO cbDAO;
	
	@Autowired
	private NoticeDAO nDAO;

	@RequestMapping("/codeboard.main.go")
	public String codeBoardMainGo(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name="category", defaultValue = "codeboard") String thisPage, Notice n, HttpServletRequest req, Member m, CodeBoard cb) {
		mDAO.islogined(req);
		cbDAO.get(req, page - 1, cb);
		
		nDAO.getNoticeByCategory(req, thisPage);
		nDAO.getCount(n, req);
		
		tg.generate(req);

		req.setAttribute("cp", "codeboard/main");
		req.setAttribute("cpSub", "codeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/codeboard.delete")
	public String codeBoardDelete(@RequestParam(name="category", defaultValue = "codeboard") String thisPage, Notice n, HttpServletRequest req, CodeBoard cb) {
		mDAO.islogined(req);
		cbDAO.delete(req, cb);
		nDAO.getNoticeByCategory(req, thisPage);
		nDAO.getCount(n, req);

		tg.generate(req);
		cbDAO.get(req, 0, cb);

		req.setAttribute("cp", "codeboard/main");
		req.setAttribute("cpSub", "codeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@DeleteMapping("/codeboard/deleteReply/{replyId}")
	public ResponseEntity<String> deleteReply(@PathVariable("replyId") Integer replyId, HttpServletRequest req, HttpServletResponse res) {
	    cbDAO.deleteReply(replyId);
	    tg.generate(req);
	    
	    return ResponseEntity.ok("댓글이 삭제되었습니다.");
	}
	
	@RequestMapping("/codeboard.get.detail")
	public String codeBoardGetDetail(HttpServletRequest req, CodeBoard cb, Member m) {
		mDAO.islogined(req);
		cbDAO.getDetail(req, cb);

		List<CodeBoardReply> replys = cbDAO.getReplysByCodeBoardNo(cb.getNo());

		tg.generate(req);

		req.setAttribute("replys", replys);
		req.setAttribute("cp", "codeboard/detail");
		req.setAttribute("cpSub", "codeboardDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}
	
	@RequestMapping("/codeboard.search")
	public String codeBoardSearch(@RequestParam(name = "page", defaultValue = "1") int page, HttpServletRequest req,
			CodeBoard cb, Member m) {
		mDAO.islogined(req);
		cbDAO.search(req, page - 1, cb);

		req.setAttribute("cp", "codeboard/main");
		req.setAttribute("cpSub", "codeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/codeboard.update")
	public String codeBoardUpdate(@RequestParam(name="category", defaultValue = "codeboard") String thisPage, Notice n, HttpServletRequest req, CodeBoard cb) {
		if (mDAO.islogined(req)) {
			cbDAO.update(req, cb);
			nDAO.getNoticeByCategory(req, thisPage);
			nDAO.getCount(n, req);
		}

		tg.generate(req);
		cbDAO.getDetail(req, cb);

		req.setAttribute("cp", "codeboard/detail");
		req.setAttribute("cpSub", "codeboardDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/codeboard.update.go")
	public String codeBoardUpdateGo(HttpServletRequest req, CodeBoard cb) {
		if (mDAO.islogined(req)) {
			cbDAO.getDetail(req, cb);
			req.setAttribute("cp", "codeboard/update");
			req.setAttribute("cpSub", "codeBoardUpdate");
		} else {
			cbDAO.getDetail(req, cb);
			req.setAttribute("cp", "codeboard/detail");
			req.setAttribute("cpSub", "codeboardDetail");
		}
		
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/codeboard.write")
	public String codeBoardWrite(@RequestParam(name="category", defaultValue = "codeboard") String thisPage, Notice n, HttpServletRequest req, Member m, CodeBoard cb) {
		if (mDAO.islogined(req)) {
			cbDAO.write(req, cb);
			cbDAO.get(req, 0, cb);
			nDAO.getNoticeByCategory(req, thisPage);
			nDAO.getCount(n, req);
		}

		tg.generate(req);

		req.setAttribute("cp", "codeboard/main");
		req.setAttribute("cpSub", "codeBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/codeboard.write.go")
	public String codeBoardWriteGo(HttpServletRequest req, Member m) {
		if (!mDAO.islogined(req)) {
			return "member/login";
		}

		tg.generate(req);

		req.setAttribute("cp", "codeboard/write");
		req.setAttribute("cpSub", "codeboardWrite");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/codeboard.reply")
	public String codeboardReply(@RequestParam("cbNo") int no, CodeBoardReply cbr, HttpServletRequest req,
			CodeBoard cb) {
		if (mDAO.islogined(req)) {
			cbDAO.reply(cbr, req);
			return "redirect:/codeboard.get.detail?no=" + no;
		}

		tg.generate(req);

		req.setAttribute("cp", "codeboard/detail");
		req.setAttribute("cpSub", "codeboardDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

}
