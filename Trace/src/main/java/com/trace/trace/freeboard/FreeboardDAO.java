package com.trace.trace.freeboard;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FreeboardDAO {

	@Autowired
	private FreeboardRepo fr;

	@Autowired
	private FreeboardCountRepo fcr;

	@Value("${file.dir}")
	private String fileDir;

	@Value("${temp.dir}")
	private String tempDir;

	public void write(HttpServletRequest req, Freeboard f) {
		File tempImage = null;
		File realImage = null;
		List<String> tempImages = new ArrayList<>();
		try {
			String token = req.getParameter("token"); // 토큰을 파라미터로 받아옴
			String lastToken = (String) req.getSession().getAttribute("lastSuccessToken");

			if (lastToken != null && token.equals(lastToken)) {
				// 글쓰기가 성공한 적이 없거나( 글 작성한 적 없음) or 현재토큰과 가져온 토큰(마지막 글쓰기 성공)이 같다면
				return;
			}
			Member m = (Member) req.getSession().getAttribute("loginMember");
			// String contents = req.getParameter("contents");
			// String files = req.getParameter("files");

			Date now = new Date();
			f.setDate(now); // freeboard 객체를 직접 수정
			// contents는 파라미터에 contents가 들어있으므로 따로 받지 않음
			f.setView(0); // 글을 처음 작성할 때 당연히, 0이므로..
			fr.save(f);
			req.getSession().setAttribute("lastSuccessToken", token);
			// 글쓰기 성공시 마지막 성공 토큰을 세션으로 보낸다.
			if (req.getSession().getAttribute("tempImage") != null) {
				tempImages = (ArrayList<String>) req.getSession().getAttribute("tempImage");
				for (String ti : tempImages) {
					tempImage = new File(tempDir + "/" + ti);
					realImage = new File(fileDir + "/" + ti);
					tempImage.renameTo(realImage);
				}
			}
			req.getSession().setAttribute("tempImage", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public void write(HttpServletRequest req,Freeboard f) {
//		String token = req.getParameter("token"); //  토큰을 파라미터로 받아옴
//		String lastToken = (String) req.getSession().getAttribute("lastSuccessToken");
//		
//		if (lastToken != null && token.equals(lastToken)) {
//			// 글쓰기가 성공한 적이 없거나( 글 작성한 적 없음) or 현재토큰과 가져온 토큰(마지막 글쓰기 성공)이 같다면
//			return;
//		}
//		Member m = (Member) req.getSession().getAttribute("loginMember");
////		String contents = req.getParameter("contents");
////		String files = req.getParameter("files");
//		
//		Date now = new Date();
//		f.setDate(now); // freeboard 객체를 직접 수정
//		// contents는 파라미터에 contents가 들어있으므로 따로 받지 않음
//		f.setView(0); // 글을 처음 작성할 때 당연히, 0이므로..
//		fr.save(f);
//		req.getSession().setAttribute("lastSuccessToken", token);
//		// 글쓰기 성공시 마지막 성공 토큰을 세션으로 보낸다.
//	}

	public void get(HttpServletRequest req) {
		// 페이지
		req.getSession().setAttribute("temp", null);
		Sort s = Sort.by(Sort.Order.desc("date")); // 정렬 방식 정의

		int page = 0; // 게시판에 들어갈 때, 페이지 기본 값은 0이여야 하므로
		if (req.getParameter("page") != null)
			page = Integer.parseInt(req.getParameter("page"));

		// 페이지번호(Page번호, 한 페이지당 몇개?, 정렬)
		Pageable p = PageRequest.of(page, 10, s);

		String search = ""; // 검색 값에 공백을 두어 첫 게시판 방문시, 전부 출력
		if (req.getParameter("search") != null) { // 사용자가 키워드를 넣고 검색을 눌렀을 때,
			search = req.getParameter("search");
		}
		req.setAttribute("search", search);
		// 검색어를 어트리뷰트로 보낸다
		// 검색어가 없으면 없는대로, 있으면 검색어가 있는 채로 리스트를 반환받아야 하기 때문

		Page<Freeboard> posts = fr.findByContentsContaining(search, p);

		req.setAttribute("posts", posts);
		// 이전 페이지, 다음 페이지의 값을 어트리뷰트로 넘겨준다.
		req.setAttribute("previousPageNum", p.previousOrFirst().getPageNumber());
		if (posts.hasNext()) { // 현재 리스트의 다음 페이지가 있다면
			req.setAttribute("nextPageNum", p.next().getPageNumber());
		} else if (posts.hasNext() == false) { // 다음 페이지가 없다면
			req.setAttribute("nextPageNum", "NoPage"); // NoPage 문자열을 보내고, Thymeleaf에서 판단
		}
	}

	public void postGet(HttpServletRequest req) {
		Freeboard f = fr.findByNo(Integer.parseInt(req.getParameter("no"))); // 파라미터명 no에서 값을 가져온다
		fr.updateView(Integer.parseInt(req.getParameter("no")));
		req.setAttribute("post", f);
		// 1. 자유게시판에서 상세보기를 눌렀을 때, 해당 게시글의 번호를 토대로 글 정보를 가져온다
		// 2. 상세보기에서 글 수정 버튼을 눌렀을 때, 해당 글의 내용을 가져온다.
	}

	public void postDel(HttpServletRequest req) {
		fr.deleteById(req.getParameter("no"));
	}

	public void postUpdate(HttpServletRequest req, Freeboard f) {

		String origin_date = req.getParameter("date_origin");
		// 2023-08-20 21:13:51.0
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date d = sdf.parse(origin_date);
			f.setDate(d);
			f.setView(Integer.valueOf(req.getParameter("view"))); // 뷰 개수 속성이 추가되었기 때문에 뷰도 세팅...
			// no, contents는 form에서 그대로 파라미터로 전송하기 때문에...

			fr.save(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void postLike(HttpServletRequest req) {
		int p_no = Integer.parseInt(req.getParameter("no"));
		Member m = (Member) req.getSession().getAttribute("loginMember");

		// 게시글의 번호를 기준으로 해당 번호의 좋아요 리스트를 가져옴
		List<FreeboardLike> likeList = fcr.findByNo(p_no);
		FreeboardLike fbl = new FreeboardLike();
		fbl.setNo(p_no);
		fbl.setWriter(m.getId());

		System.out.println(fbl.getWriter() + " : " + fbl.getNo() + " : " + fbl.getLikeNo());
		boolean isExist = false;

		for (FreeboardLike freeboardLike : likeList) {
			if (freeboardLike.getWriter().equals(m.getId())) { // DB에 있다면
				System.out.println("사용자가 Like DB에 존재");
				fcr.deleteLike(p_no, m.getId());
				isExist = true;
				break;
			}
		}
		if (isExist == false) { // 반복문에서 DB에 존재 하지 않았다면
			System.out.println("사용자가 Like DB에 존재하지 않음");
			fcr.save(fbl);
		}

		List<FreeboardLike> afterList = fcr.findByNo(p_no);
		req.setAttribute("like", afterList.size());

	}

	public void postLikeGet(HttpServletRequest req) {
		req.setAttribute("like", (fcr.findByNo(Integer.valueOf(req.getParameter("no")))).size());
	}

}
