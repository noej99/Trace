package com.trace.trace.dataroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trace.trace.Tokengenerator;
import com.trace.trace.member.MemberDAO;
import com.trace.trace.notice.Notice;
import com.trace.trace.notice.NoticeDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class DataRoomController {

	@Autowired
	private DataRoomDAO drDAO;

	@Autowired
	private Tokengenerator tg;

	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private NoticeDAO nDAO;

	@RequestMapping("/dataroom")
	public String dataroom(HttpServletRequest req, @RequestParam(name="category", defaultValue = "databoard") String thisPage, Notice n) {
		drDAO.get(req);
		nDAO.getNoticeByCategory(req, thisPage);
		nDAO.getCount(n, req);
		tg.generate(req);
		mDAO.islogined(req);
		req.setAttribute("cp", "dataroom/dataRoom");
		req.setAttribute("cpSub", "dataRoomList");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}

	@RequestMapping("/dataroom.reply")
	public String dataroomReply(@RequestParam("drNo") int no, DataRoomReply drr, HttpServletRequest req) {
		tg.generate(req);
		if (mDAO.islogined(req)) {
			drDAO.reply(drr, req);
		}
		drDAO.view(no, req);
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		req.setAttribute("cp", "dataroom/dataRoomView");
		req.setAttribute("cpSub", "dataRoomList");
		return "index";
	}
	
	@RequestMapping("/dataroom.reReply")
	public String dataroomReReply(@RequestParam("drNo") int no, DataRoomReReply drrr, HttpServletRequest req) {
		tg.generate(req);
		if (mDAO.islogined(req)) {
			drDAO.reReply(drrr, req);
		}
		drDAO.view(no, req);
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		req.setAttribute("cp", "dataroom/dataRoomView");
		req.setAttribute("cpSub", "dataRoomList");
		return "index";
	}
	
	@RequestMapping("/dataroom.deleteReply")
	public String dataroomDeleteReply(int no, int rNo, HttpServletRequest req) {
		if (mDAO.islogined(req)) {
			drDAO.deleteReply(no, rNo);
		}
		drDAO.view(no, req);
		req.setAttribute("cp", "dataroom/dataRoomView");
		req.setAttribute("cpSub", "dataRoomList");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}
	
	@RequestMapping("/dataroom.deleteReReply")
	public String dataroomDeleteReReply(int no, int rrNo, HttpServletRequest req) {
		if (mDAO.islogined(req)) {
			drDAO.deleteReply(no, rrNo);
		}
		drDAO.view(no, req);
		req.setAttribute("cp", "dataroom/dataRoomView");
		req.setAttribute("cpSub", "dataRoomList");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}

	@RequestMapping("/dataroom.view")
	public String dataroomView(@RequestParam("no") int no, HttpServletRequest req) {
		req.getSession().setAttribute("temp", null);
		tg.generate(req);
		drDAO.view(no, req);
		mDAO.islogined(req);
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		req.setAttribute("cp", "dataroom/dataRoomView");
		req.setAttribute("cpSub", "dataRoomList");
		return "index";
	}
	
	@RequestMapping(value = "/dataroom.like", produces = "application/json;charset=utf-8")
	public @ResponseBody String dataroomLike(DataRoom dr, HttpServletRequest req, HttpServletResponse res) {
		String result = "{ \"result\" : \"오류가 발생했습니다\" }";
		res.addHeader("Access-Control-Allow-Origin", "*");
		if(mDAO.islogined(req)) {
			result = drDAO.Like(dr);
		}
		return result;
	}

	@RequestMapping("/dataroom.write")
	public String dataroomWrite(HttpServletRequest req, @RequestParam(name="category", defaultValue = "databoard") String thisPage, Notice n) {
		tg.generate(req);
		if (mDAO.islogined(req)) {
			req.setAttribute("cp", "dataroom/dataRoomWrite");
			req.setAttribute("cpSub", "writeForm");
		} else {
			drDAO.get(req);
			nDAO.getNoticeByCategory(req, thisPage);
			nDAO.getCount(n, req);
			req.setAttribute("cp", "dataroom/dataRoom");
			req.setAttribute("cpSub", "dataRoomList");
		}
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}

	@RequestMapping(value = "/dataroom.uploadImage", produces = "application/json; charset=utf8")
	public @ResponseBody String dataroomUploadImage(@RequestParam("imgFile") MultipartFile mf, HttpServletRequest req,
			HttpServletResponse res) {
//		res.addHeader("Access-Control-Allow-Origin", "*");
		req.getSession().setAttribute("temp", true);
		return drDAO.uploadImage(mf, req);
	}

	@RequestMapping(value = "/dataroom.image/{name}")
	public @ResponseBody Resource dataroomImage(@PathVariable("name") String name, HttpServletRequest req) {
		return drDAO.getImage(name, req);
	}
	
	@RequestMapping("/dataroom.download/{name}")
	public ResponseEntity<Resource> getFile(@PathVariable("name") String name) {
		return drDAO.getFile(name);
	}

	@RequestMapping("/dataroom.upload")
	public String dataroomUpload(@RequestParam("files") MultipartFile[] mf, DataRoom dr, HttpServletRequest req, @RequestParam(name="category", defaultValue = "databoard") String thisPage, Notice n) {
		tg.generate(req);
		if (mDAO.islogined(req)) {
			drDAO.upload(mf, dr, req);
		}
		drDAO.get(req);
		nDAO.getNoticeByCategory(req, thisPage);
		nDAO.getCount(n, req);
		req.setAttribute("cp", "dataroom/dataRoom");
		req.setAttribute("cpSub", "dataRoomList");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		req.getSession().setAttribute("temp", null);
		return "index";
	}

	@RequestMapping("/dataroom.delete")
	public String dataroomDelete(int no, HttpServletRequest req, @RequestParam(name="category", defaultValue = "databoard") String thisPage, Notice n) {
		if (mDAO.islogined(req)) {
			drDAO.delete(no, req);
		}
		drDAO.get(req);
		nDAO.getNoticeByCategory(req, thisPage);
		nDAO.getCount(n, req);
		req.setAttribute("cp", "dataroom/dataRoom");
		req.setAttribute("cpSub", "dataRoomList");
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}

	@RequestMapping("/dataroom.modify")
	public String dataroomModify(DataRoom dr, HttpServletRequest req, @RequestParam(name="category", defaultValue = "databoard") String thisPage, Notice n) {
		tg.generate(req);
		if (mDAO.islogined(req)) {
			drDAO.modify(req);
			req.setAttribute("cp", "dataroom/dataRoomModify");
			req.setAttribute("cpSub", "modifyForm");
		} else {
			drDAO.get(req);
			nDAO.getNoticeByCategory(req, thisPage);
			nDAO.getCount(n, req);
			req.setAttribute("cp", "dataroom/dataRoom");
			req.setAttribute("cpSub", "dataRoomList");
		}
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}

	@RequestMapping("/dataroom.update")
	public String dataroomUpdate(DataRoom dr, HttpServletRequest req) {
		tg.generate(req);
		if (mDAO.islogined(req)) {
			drDAO.update(dr, req);
		}
		req.getSession().setAttribute("temp", null);
		tg.generate(req);
		drDAO.view(Integer.parseInt(req.getParameter("modNo")), req);
		mDAO.islogined(req);
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		req.setAttribute("cp", "dataroom/dataRoomView");
		req.setAttribute("cpSub", "dataRoomList");
		return "index";
	}

	@RequestMapping("/dataroom.search")
	public String dataroomSearch(DataRoom dr, HttpServletRequest req, @RequestParam(name="category", defaultValue = "databoard") String thisPage, Notice n) {
		drDAO.search(req);
		nDAO.getNoticeByCategory(req, thisPage);
		nDAO.getCount(n, req);
		req.setAttribute("cp", "dataroom/dataRoomSearch");
		req.setAttribute("cpSub", "dataRoomList");
		mDAO.islogined(req);
		req.setAttribute("loginPage", "member/logined");
		req.setAttribute("loginSub", "loginedM");
		return "index";
	}

//	@RequestMapping("/dataroom.test")
//	public String dataroomTest(HttpServletRequest req) {
//		tg.generate(req);
//		for (int i = 0; i <= 365; i++) {
//			drDAO.test(req, i);
//			System.out.println(i);
//		}
//		drDAO.get(req);
//		req.setAttribute("cp", "dataroom/dataRoom");
//		req.setAttribute("cpSub", "dataRoomList");
//		mDAO.islogined(req);
//		req.setAttribute("loginPage", "member/logined");
//		req.setAttribute("loginSub", "loginedM");
//		return "index";
//	}
}
