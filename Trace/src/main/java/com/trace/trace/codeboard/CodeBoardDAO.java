package com.trace.trace.codeboard;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.trace.trace.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CodeBoardDAO {
	
	@Autowired
	private CodeBoardRepo cbr;
	
	@Autowired
	private CodeBoardReplyRepo cbrr;
	
	
	public void delete(HttpServletRequest req, CodeBoard cb) {
		try {
			cbr.deleteById(cb.getNo());
			req.setAttribute("result", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제실패");
		}
	}
	
	public void deleteReply(Integer replyId) {
	    try {
	        cbrr.deleteById(replyId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void get(HttpServletRequest req, int page, CodeBoard cb) {
		try {
			List<CodeBoard> count = cbr.findAll();
			int totalPage = (int) Math.ceil(count.size() / 10.0);
			int start = (int)((Math.floor(page / 10) * 10) + 1 <= totalPage ? (Math.floor(page / 10) * 10) + 1 : totalPage);
			int end = (start + 9 < totalPage ? start + 9 : totalPage);

			Sort s = Sort.by(Sort.Order.desc("no"));
			Pageable p = PageRequest.of(page, 10, s);
			
			List<CodeBoard> codeBoards = cbr.findByTitleContaining("", p);
			
			req.setAttribute("start", start);
			req.setAttribute("end", end);
			req.setAttribute("pageCount", totalPage);
			req.setAttribute("codeBoards", codeBoards);
			req.setAttribute("currentPage", page + 1);
			
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getDetail(HttpServletRequest req, CodeBoard cb) {
		try {
			List<CodeBoard> details = cbr.findByNoEquals(cb.getNo());
			
			req.setAttribute("cbDetails", details);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search(HttpServletRequest req, int page, CodeBoard cb) {
		try {
			String cate = req.getParameter("category");
			String keyword = req.getParameter("keyword");
			
			Sort s = Sort.by(Sort.Order.desc("date"));
			Pageable p = PageRequest.of(page, 10, s);
			
			List<CodeBoard> count = null;
			List<CodeBoard> codeBoards = null;
			
			if (cate.equals("title")) {
				count = cbr.findByTitleContaining(keyword);
				codeBoards = cbr.findByTitleContaining(keyword, p);
			} else if (cate.equals("txt")) {
				count = cbr.findByTxtContaining(keyword);
				codeBoards = cbr.findByTxtContaining(keyword, p);
			}
			
			int totalPage = (int) Math.ceil(count.size() / 10.0);
			int start = (int)((Math.floor(page / 10) * 10) + 1 <= totalPage ? (Math.floor(page / 10) * 10) + 1 : totalPage);
			int end = (start + 9 < totalPage ? start + 9 : totalPage);
			
			req.setAttribute("start", start);
			req.setAttribute("end", end);
			req.setAttribute("pageCount", totalPage);
			req.setAttribute("currentPage", page + 1);
			req.setAttribute("codeBoards", codeBoards);
			req.setAttribute("category", cate);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update (HttpServletRequest req, CodeBoard cb) {
		try {
			cb = cbr.findById(cb.getNo()).get();
			
			Member m = (Member)req.getSession().getAttribute("loginMember");
			
			cb.setTitle(req.getParameter("title"));
			cb.setTxt(req.getParameter("txt"));
			
			cbr.save(cb);
			req.setAttribute("result", "수정 성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "수정 실패");
		}
	}

	
	public void write(HttpServletRequest req, CodeBoard cb) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");

			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "글쓰기 실패(새로고침)");
				return;
			}
			
			Member m = (Member)req.getSession().getAttribute("loginMember");
			String txt = req.getParameter("txt");
			txt = txt.replace("\r\n", "<br>");
			
			cb.setDate(new Date());
			cbr.save(cb);
			
			req.setAttribute("result", "글쓰기 성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "글쓰기 실패");
		}
	}
	
	public void reply(CodeBoardReply cbr, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "댓글쓰기실패(새로고침)");
				return;
			}
			
	        Member m = (Member) req.getSession().getAttribute("loginMember");
			cbr.setDate(new Date());
			cbrr.save(cbr);
			
			req.setAttribute("result", "댓글쓰기성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글쓰기실패");
		}
	}
	
    public List<CodeBoardReply> getReplysByCodeBoardNo(Integer cbNo) {
        try {
            return cbrr.findReplysByCodeBoardNO(cbNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
