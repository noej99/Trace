package com.trace.trace.qb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class QBController {

	@Autowired
	private Tokengenerator tGT;

	@Autowired
	private MemberDAO mDAO;

	@Autowired
	private QBDAO qDAO;

	@Autowired
	private NoticeDAO nDAO;
	
	@RequestMapping("/qb.go")
	public String qbGo(Member m, HttpServletRequest req, @RequestParam(name = "category", defaultValue = "qnaboard") String q, Notice n) {
		mDAO.islogined(req);
		qDAO.get(req);
		qDAO.getCount(req);
		nDAO.getNoticeByCategory(req, q);
		nDAO.getCount(n, req);
		
		req.setAttribute("cp", "qb/qb");
		req.setAttribute("cpSub", "qaBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/qb.search")
	public String qbSearch(HttpServletRequest req, @RequestParam(name = "category", defaultValue = "qnaboard") String q, Notice n) {
		mDAO.islogined(req);
		qDAO.search(req);
		qDAO.getCount(req);
		nDAO.getNoticeByCategory(req, q);
		nDAO.getCount(n, req);
		
		req.setAttribute("cp", "qb/qb");
		req.setAttribute("cpSub", "qaBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}
	
	@RequestMapping("/qb.write.go")
	public String qbWriteGo(HttpServletRequest req) {
		mDAO.islogined(req);
		req.setAttribute("cp", "qb/qbWrite");
		req.setAttribute("cpSub", "qbWrite");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		tGT.generate(req);
		return "index";
	}

	@RequestMapping("/qb.write")
	public String qbWrite(QB qb, QBCount qbc, HttpServletRequest req, @RequestParam(name = "category", defaultValue = "qnaboard") String q, Notice n) {
		if (mDAO.islogined(req)) {
			qDAO.write(qb, qbc, req);
		} 
		qDAO.get(req);
		qDAO.getCount(req);
		nDAO.getNoticeByCategory(req, q);
		nDAO.getCount(n, req);
		req.setAttribute("cp", "qb/qb");
		req.setAttribute("cpSub", "qaBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/qb.reply.write")
	public String qbWrite(QB qb, QBCount qbc, QBReply qbr, QBReReply qbrr, HttpServletRequest req) {
		if (mDAO.islogined(req)) {
			qDAO.replyWrite(qb, qbr, qbrr, req);
			qDAO.getDetail(qb, qbc, qbr, req);
		}
		qDAO.getDetail(qb, qbc, qbr, req);
		req.setAttribute("cp", "qb/detail");
		req.setAttribute("cpSub", "qbDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		tGT.generate(req);
		return "index";
	}

	@RequestMapping("/qb.detail")
	public String qbDetail(QB qb, QBCount qbc, QBReply qbr, HttpServletRequest req) {
		mDAO.islogined(req);
		qDAO.getDetail(qb, qbc, qbr, req);
		qDAO.updateView(qb, qbc);
		
		req.setAttribute("cp", "qb/detail");
		req.setAttribute("cpSub", "qbDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		tGT.generate(req);
		return "index";
	}

	@RequestMapping("/qb.update.go")
	public String qbUpdateGo(QB qb, QBCount qbc, QBReply qbr, HttpServletRequest req) {
		if (mDAO.islogined(req)) {
			qDAO.getDetail(qb, qbc, qbr, req);
		}
		req.setAttribute("cp", "qb/qbUpdate");
		req.setAttribute("cpSub", "qbUpdate");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		return "index";
	}

	@RequestMapping("/qb.update")
	public String qbUpdate(QB qb, QBCount qbc, QBReply qbr, HttpServletRequest req) {
		if (mDAO.islogined(req)) {
			qDAO.update(qb, req);
		}
		qDAO.getDetail(qb, qbc, qbr, req);
		req.setAttribute("cp", "qb/detail");
		req.setAttribute("cpSub", "qbDetail");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		
		tGT.generate(req);
		return "index";
	}

	@RequestMapping("/qb.delete")
	public String qbDelete(Member m, QB qb, HttpServletRequest req, @RequestParam(name = "category", defaultValue = "qnaboard") String q, Notice n) {
		if (mDAO.islogined(req)) {
			qDAO.delete(qb);
		} 
		qDAO.get(req);
		qDAO.getCount(req);
		nDAO.getNoticeByCategory(req, q);
		nDAO.getCount(n, req);
		
		req.setAttribute("cp", "qb/qb");
		req.setAttribute("cpSub", "qaBoard");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");

		tGT.generate(req);
		return "index";
	}

	@DeleteMapping("/qb/reply/{replyId}")
	public ResponseEntity<String> deleteReply(@PathVariable("replyId") Integer replyId, HttpServletRequest req, HttpServletResponse res) {
		qDAO.ReplyDelete(replyId);
		tGT.generate(req);

		return ResponseEntity.ok("댓글이 삭제되었습니다.");
	}

	@DeleteMapping("/qb/rereply/{rereplyId}")
	public ResponseEntity<String> deleteReReply(@PathVariable("rereplyId") Integer rereplyId, HttpServletRequest req, HttpServletResponse res) {
		qDAO.ReReplyDelete(rereplyId);
		tGT.generate(req);
		
		return ResponseEntity.ok("댓글이 삭제되었습니다.");
	}
	
	@ResponseBody
    @PostMapping("/update.like")
    public void updateLike(QBCount qbc, QBLikeCheck qblc, HttpServletRequest req) {
		qDAO.updateLike(qbc, qblc, req);
		
		tGT.generate(req);
	}

}
