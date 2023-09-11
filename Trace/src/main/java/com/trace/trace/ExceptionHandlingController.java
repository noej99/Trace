package com.trace.trace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trace.trace.codeboard.CodeBoard;
import com.trace.trace.codeboard.CodeBoardDAO;
import com.trace.trace.dataroom.DataRoomDAO;
import com.trace.trace.freeboard.FreeboardDAO;
import com.trace.trace.member.MemberDAO;
import com.trace.trace.notice.Notice;
import com.trace.trace.notice.NoticeDAO;
import com.trace.trace.qb.QBDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ExceptionHandlingController implements ErrorController {

	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private NoticeDAO nDAO;
	@Autowired
	private QBDAO qDAO;
	@Autowired
	private DataRoomDAO drDAO;
	@Autowired
	private FreeboardDAO fDAO;
	@Autowired
	private CodeBoardDAO cDAO;
	
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request,Notice n, CodeBoard c) {
		mDAO.islogined(request);
		nDAO.get(request, 0, n);
		qDAO.get(request);
		drDAO.get(request);
		fDAO.get(request);
		cDAO.get(request, 0, c);

		request.setAttribute("cp", "home");
		request.setAttribute("cpSub", "homeboard");
		request.setAttribute("loginPage", "member/logined");
		request.setAttribute("loginSub", "loginedM");
       
        return "index"; 
    }
	 
	
}
