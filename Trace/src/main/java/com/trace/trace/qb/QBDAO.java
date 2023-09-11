package com.trace.trace.qb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class QBDAO {

	@Autowired
	private QBRepo qr;

	@Autowired
	private QBCountRepo qcr;

	@Autowired
	private QBReplyRepo qbrr;

	@Autowired
	private QBReReplyRepo qbrrr;

	@Autowired
	private QBLikeCheckRepo qblcr;
	
	public void get(HttpServletRequest req) {
	    try {
	    	int page = 0;
	    	
	    	int perPage = 10;
	    	int pageSize = 10;
	    	
	        if (req.getParameter("page") != null) {
	        	page = Integer.parseInt(req.getParameter("page")) - 1;
			}

	        Sort s = Sort.by(Sort.Order.desc("no"));
	        Pageable pageSort = PageRequest.of(page, perPage, s);

	        Page<QB> qbPage = qr.findAll(pageSort);

	        int curPage = qbPage.getNumber();
			int maxPage = qbPage.getTotalPages();
			int startPage = curPage / pageSize * pageSize + 1;
			int endPage = (startPage + pageSize - 1 > maxPage ? maxPage : startPage + pageSize - 1); 
			List<Integer> pSize = new ArrayList<>();
			for (int i = startPage; i <= endPage; i++) {
				pSize.add(i);
			}
			
			if (startPage == 1) {
				startPage = 2;
			}
			
	        req.setAttribute("qb", qbPage);
	        req.setAttribute("qbIsFirst", qbPage.isFirst());
	        req.setAttribute("qbIsLast", qbPage.isLast());
	        req.setAttribute("qbPageNumber", curPage + 1);
	        req.setAttribute("pSize", pSize);
	        req.setAttribute("qbStartPage", startPage);
	        req.setAttribute("qbEndPage", endPage);
	        req.setAttribute("qbLast", maxPage);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void search(HttpServletRequest req) {
		try {
			int page = 0;
			int perPage = 10;
			int pageSize = 10;

			String category = req.getParameter("cate");
			String keyword = req.getParameter("search");
			
			if (req.getParameter("page") != null) {
				page = Integer.parseInt(req.getParameter("page")) - 1;
			}
			
			Sort s = Sort.by(Sort.Order.desc("no"));
			Pageable pageSort = PageRequest.of(page, perPage, s);
			
			Page<QB> qbPage = null;
			
			if (category != null) {
				if (keyword != null) {
					qbPage = qr.findByCateAndTitleContaining(category, keyword, pageSort);
				} else {
					qbPage = qr.findByCateContaining(category, pageSort);
				}
			} else {
				qbPage = qr.findByTitleContaining(keyword, pageSort);
			}
			
			if (qbPage == null || qbPage.getTotalElements() == 0) {
				req.setAttribute("qb", null);
			} else {
				req.setAttribute("qb", qbPage);
			}
			
			int curPage = qbPage.getNumber();
			int maxPage = qbPage.getTotalPages();
			int startPage = curPage / pageSize * pageSize + 1;
			int endPage = (startPage + pageSize - 1 > maxPage ? maxPage : startPage + pageSize - 1); 
			List<Integer> pSize = new ArrayList<>();
			for (int i = startPage; i <= endPage; i++) {
				pSize.add(i);
			}
			
			req.setAttribute("qbIsFirst", qbPage.isFirst());
			req.setAttribute("qbIsLast", qbPage.isLast());
			req.setAttribute("qbPageNumber", curPage + 1);
			req.setAttribute("pSize", pSize);
	        req.setAttribute("qbStartPage", startPage);
	        req.setAttribute("qbEndPage", endPage);
	        req.setAttribute("qbLast", maxPage);
			req.setAttribute("qbKey", keyword);
			req.setAttribute("qbCate", category);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCount(HttpServletRequest req) {
		try {
			List<QBCount> qbc = qcr.findAll();
			req.setAttribute("qbc", qbc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDetail(QB qb, QBCount qbc, QBReply qbr, HttpServletRequest req) {
		try {
			List<QB> qbs = qr.findByNoEquals(qb.getNo());
			req.setAttribute("qbDetails", qbs);

			List<QBCount> qbcs = qcr.findByNo(qb.getNo());
			req.setAttribute("qbcDetails", qbcs);

//			List<QBReply> qbrs = qbrr.findByQaboard(qb);
//			req.setAttribute("qbr", qbrs);

			req.setAttribute("qbrCount", qbrr.countByQaboard(qb));

//			List<QBReReply> qbrrs = qbrrr.findAll();
//			req.setAttribute("qbrrs", qbrrs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(QB qb, QBCount qbc, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				return;
			}
			
			Date now = new Date();
			qb.setCate(req.getParameter("cate"));
			qb.setDate(now);
			qr.save(qb);

			int no = qb.getNo();

			qbc.setNo(no);
			qbc.setLike(0);
			qbc.setView(0);
			qcr.save(qbc);

			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void replyWrite(QB qb, QBReply qbr, QBReReply qbrr2, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String lastToken = (String) req.getSession().getAttribute("successToken");
			if (lastToken != null && token.equals(lastToken)) {
				return;
			}
			
			String qbrqrno = req.getParameter("qrno");
			
			if (qbrqrno.isEmpty()) {
				qbr.setQaboard(qb);
				Date now = new Date();
				qbr.setDate(now);
				qbrr.save(qbr);
			} else {
				qbrr2.setQbReReply(qbr);
				Date now = new Date();
				qbrr2.setDate(now);
				qbrrr.save(qbrr2);
			}

			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(QB qb) {
		try {
			qr.deleteById(qb.getNo());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ReplyDelete(Integer replyId) {
		try {
			qbrr.deleteById(replyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ReReplyDelete(Integer rereplyId) {
		try {
			qbrrr.deleteById(rereplyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(QB qb, HttpServletRequest req) {
		try {
			qb = qr.findById(qb.getNo()).get();

			qb.setCate(req.getParameter("cate"));
			qb.setTitle(req.getParameter("title"));
			qb.setTxt(req.getParameter("txt"));

			qr.save(qb);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateView(QB qb, QBCount qbc) {
		try {
			List<QBCount> qc = qcr.findByNo(qb.getNo());
			int v = qc.get(0).getView() + 1;
			System.out.println(qc.get(0).getLike());
			qbc.setView(v);
			qbc.setLike(qc.get(0).getLike());
			qcr.save(qbc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLike(QBCount qbc, QBLikeCheck qblc, HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("qbNo"));
			Member loginMember = (Member) req.getSession().getAttribute("loginMember");
			String memberId = loginMember.getId();
			List<QBLikeCheck> qlc = qblcr.findByQlcIdAndQlcno(memberId, no);
			List<QBCount> qc = qcr.findByNo(no);
			if (qlc.isEmpty()) {
				qblc.setQlcno(no);
				qblc.setQlcId(memberId);
				qblc.setCheck(0);
				qblcr.save(qblc);
				qlc = qblcr.findByQlcIdAndQlcno(memberId, no);
			}
			int l = 0;
			if (qlc.get(0).getQlcno() == no) {
				if (qlc.get(0).getCheck() == 0) {
					l = qc.get(0).getLike() + 1;
					qlc.get(0).setCheck(1);
				} else {
					l = qc.get(0).getLike() - 1;
					qlc.get(0).setCheck(0);
				}
				qbc.setNo(no);
				qbc.setLike(l);
				qbc.setView(qc.get(0).getView());
				qcr.save(qbc);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
