package com.trace.trace.notice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class NoticeDAO {
	
	@Autowired
	private NoticeRepo nr;
	
	@Autowired
	private NoticeCountRepo ncr;
	
	@Autowired
	private NoticeLikeCheckRepo nlcr;
	
	@Transactional
	public void delete(Notice n, HttpServletRequest req) {
		try {
			nr.deleteByNo(n.getNo());
			req.setAttribute("result", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제실패");
		}
	}
	
	
	public void get(HttpServletRequest req, int page, Notice n) {
		try {
			List<Notice> count = nr.findAll();
			int totalPage = (int) Math.ceil(count.size() / 10.0);
			int start = (int)((Math.floor(page / 10) * 10) + 1 <= totalPage ? (Math.floor(page / 10) * 10) + 1 : totalPage);
			int end = (start + 9 < totalPage ? start + 9 : totalPage);
			
			Sort s = Sort.by(Sort.Order.desc("date"));
			Pageable p = PageRequest.of(page, 10, s);
			
			List<Notice> notices = nr.findByCategoryAndTitleContaining("notice", "", p);
				
			req.setAttribute("start", start);
			req.setAttribute("end", end);
			req.setAttribute("pageCount", totalPage);
			req.setAttribute("notices", notices);
			req.setAttribute("currentPage", page + 1);
			
			getCount(n, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getCount(Notice n, HttpServletRequest req) {
		try {
			List<NoticeCount> nc = ncr.findAll();
			req.setAttribute("counts", nc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getDetail(Notice n, NoticeCount nc, HttpServletRequest req) {
		try {
			List<Notice> details = nr.findByNoEquals(n.getNo());
			List<NoticeCount> count = ncr.findByNo(n.getNo());
			
			nc.setView(count.get(0).getView() + 1);
			nc.setLike(count.get(0).getLike());
			ncr.save(nc);
			
			req.setAttribute("details", details);
			req.setAttribute("count", count);
			getCount(n, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDetail2(Notice n, NoticeCount nc, HttpServletRequest req) {
		try {
			HttpSession session = req.getSession();
			String noticeNo = n.getNo().toString();

			if (session.getAttribute("viewed_" + noticeNo) == null) {
				List<Notice> details = nr.findByNoEquals(n.getNo());
				List<NoticeCount> count = ncr.findByNo(n.getNo());

				nc.setView(count.get(0).getView() + 1);
				nc.setLike(count.get(0).getLike());
				ncr.save(nc);

				session.setAttribute("viewed_" + noticeNo, true);

				req.setAttribute("details", details);
				req.setAttribute("count", count);
				getCount(n, req);
			} else {
				List<Notice> details = nr.findByNoEquals(n.getNo());
				List<NoticeCount> count = ncr.findByNo(n.getNo());

				req.setAttribute("details", details);
				req.setAttribute("count", count);
				getCount(n, req);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getNoticeByCategory(HttpServletRequest req, String thisPage) {
		try {
			List<Notice> categoryNotice = null;
			
			Sort s = Sort.by(Sort.Order.desc("date"));
			Pageable p = PageRequest.of(0, 3, s);
			
			if (thisPage.equals("databoard")) {
				categoryNotice = nr.findByCategoryEquals("droom", p);
			} else if (thisPage.equals("qnaboard")) {
				categoryNotice = nr.findByCategoryEquals("qna", p);
			} else if (thisPage.equals("freeboard")) {
				categoryNotice = nr.findByCategoryEquals("free", p);
			} else if (thisPage.equals("codeboard")) {
				categoryNotice = nr.findByCategoryEquals("code", p);
			} 
			
			req.setAttribute("cateNotice", categoryNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search(Notice n, int page, HttpServletRequest req) {
		try {
			String cate = req.getParameter("category");
			String keyword = req.getParameter("keyword");
			
			Sort s = Sort.by(Sort.Order.desc("date"));
			Pageable p = PageRequest.of(page, 10, s);
			
			List<Notice> count = null;
			List<Notice> notices = null;
			
			if (cate.equals("title")) {
				count = nr.findByTitleContaining(keyword);
				notices = nr.findByTitleContaining(keyword, p);
			} else if (cate.equals("content")) {
				count = nr.findByContentsContaining(keyword);
				notices = nr.findByContentsContaining(keyword, p);
			}
			
			int totalPage = (int) Math.ceil(count.size() / 10.0);
			int start = (int)((Math.floor(page / 10) * 10) + 1 <= totalPage ? (Math.floor(page / 10) * 10) + 1 : totalPage);
			int end = (start + 9 < totalPage ? start + 9 : totalPage);
			
			req.setAttribute("start", start);
			req.setAttribute("end", end);
			req.setAttribute("pageCount", totalPage);
			req.setAttribute("currentPage", page + 1);
			req.setAttribute("notices", notices);
			req.setAttribute("category", cate);
			req.setAttribute("keyword", keyword);
			
			getCount(n, req);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update (Notice n, HttpServletRequest req) {
		try {
			n = nr.findById(n.getNo()).get();
			n.setNo(Integer.parseInt(req.getParameter("no")));
			n.setTitle(req.getParameter("dtitle"));
			n.setContents(req.getParameter("dcontents"));
			
			nr.save(n);
			req.setAttribute("result", "수정 성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "수정 실패");
		}
	}
	
	public void updateLikeCheck(HttpServletRequest req, NoticeCount nct, NoticeLikeCheck nlc) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			int like = 0;
			Member loginMember = (Member) req.getSession().getAttribute("loginMember");
			String Id = loginMember.getId();
			List<NoticeLikeCheck> nlck = nlcr.findByNoAndUser(no, Id);
			List<NoticeCount> nc = ncr.findByNo(no);
			
			System.out.println(no);
			
			if (nlck.isEmpty()) {
				nlc.setNo(no);
				nlc.setUser(Id);
				nlc.setCheck(0);
				nlcr.save(nlc);
				nlck = nlcr.findByNoAndUser(no, Id);
			}
			
			if (nlck.get(0).getNo() == no) {
				if (nlck.get(0).getCheck() == 0) {
					like = nc.get(0).getLike() + 1;
					nlck.get(0).setCheck(1);
				} else {
					like = nc.get(0).getLike() - 1;
					nlck.get(0).setCheck(0);
				}
				nct.setNo(no);
				nct.setLike(like);
				nct.setView(nc.get(0).getView());
				ncr.save(nct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(Notice n, NoticeCount nc, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");

			if (lastToken != null && token.equals(lastToken)) {
				req.setAttribute("result", "글쓰기 실패(새로고침)");
				return;
			}
			
			String boardCate = req.getParameter("boardCate");
			
			n.setDate(new Date());
			n.setCategory(boardCate);
			nr.save(n);
			
			nc.setNo(n.getNo());
			nc.setView(0);
			nc.setLike(0);
			ncr.save(nc);
			
			req.setAttribute("result", "글쓰기 성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "글쓰기 실패");
		}
	}
}
