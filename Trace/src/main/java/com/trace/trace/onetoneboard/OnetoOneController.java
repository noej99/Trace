package com.trace.trace.onetoneboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trace.trace.Tokengenerator;
import com.trace.trace.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OnetoOneController {

	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private OnetoOneDAO otobDAO;
	
	@Autowired
	private Tokengenerator tgt;
	
	@RequestMapping("/go.otob")
	public String goOnetoOneBoard(HttpServletRequest request) {
		if (mDAO.islogined(request)) {
			
			tgt.generate(request);
			mDAO.islogined(request);
			otobDAO.get(request);
			request.setAttribute("cp", "otoboard/otobmain");
			request.setAttribute("cpSub", "otobItem");
			request.setAttribute("loginPage", "member/logined");
			request.setAttribute("loginSub", "loginedM");
			return "index";
		}else {
			return "member/login";
		}
		
	}
	@RequestMapping("/delete.otob")
	public String deleteOnetoOneBoard(HttpServletRequest request) {
		tgt.generate(request);
		mDAO.islogined(request);
		otobDAO.delete(request);
		otobDAO.get(request);
		request.setAttribute("cp", "otoboard/otobmain");
		request.setAttribute("cpSub", "otobItem");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	@RequestMapping("/write.otob")
	public String writeOnetoOneBoard(HttpServletRequest request,OnetoOneBoard ob) {
		tgt.generate(request);
		mDAO.islogined(request);
		otobDAO.write(request, ob);
		otobDAO.get(request);
		request.setAttribute("cp", "otoboard/otobmain");
		request.setAttribute("cpSub", "otobItem");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	@RequestMapping("/answer.otob")
	public String answerOnetoOneBoard(HttpServletRequest request,OnetoOneBoard ob) {
		tgt.generate(request);
		mDAO.islogined(request);
		otobDAO.answer(request, ob);
		otobDAO.get(request);
		request.setAttribute("cp", "otoboard/otobmain");
		request.setAttribute("cpSub", "otobItem");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	@RequestMapping("/get.detail.otob")
	public String getDeatilOnetoOneBoard(OnetoOneBoard ob,HttpServletRequest request) {
		tgt.generate(request);
		mDAO.islogined(request);
		otobDAO.getDetail(request, ob);
		request.setAttribute("cp", "otoboard/otobdetail");
		request.setAttribute("cpSub", "otobDeatil");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
		return "index";
		
	}
	
}
