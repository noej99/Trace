package com.trace.trace.onetoneboard;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trace.trace.member.Member;
import com.trace.trace.member.MemberWriter;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OnetoOneDAO {

	@Autowired
	private OnetoOneRepo oor;
	
	public void write(HttpServletRequest request,OnetoOneBoard oob) {
		try {
			Member m=(Member) request.getSession().getAttribute("loginMember");
			MemberWriter mwr= new MemberWriter();
			mwr.setId1(m.getId());
			String token = request.getParameter("token");
			System.out.println(token);
			String lastToken = (String) request.getSession().getAttribute("successToken");
			System.out.println(lastToken);
			if (lastToken != null && token.equals(lastToken)) {
				return;
			}
			oob.setWriter(mwr);
			Date now=new Date();
			oob.setStatus(0);
			oob.setDate(now);
			oor.save(oob);
			request.getSession().setAttribute("successToken", token);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void get(HttpServletRequest request) {
		try {
			List<OnetoOneBoard>otob=(List<OnetoOneBoard>) oor.findAll();
			request.setAttribute("otob", otob);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	}
	public void getDetail(HttpServletRequest request,OnetoOneBoard otobb) {
		try {
			otobb.setNo(Integer.parseInt(request.getParameter("no")));
			List<OnetoOneBoard>otob=oor.findByNoEquals(otobb.getNo());
			request.setAttribute("otobdetails", otob);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete(HttpServletRequest request) {
		try {
			oor.deleteById(Integer.parseInt(request.getParameter("no")));
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void answer(HttpServletRequest request,OnetoOneBoard otobb) {
		try {
			List<OnetoOneBoard>otob=oor.findByNoEquals(otobb.getNo());
			OnetoOneBoard o = oor.findById(otob.get(0).getNo()).get();
			Date now=new Date();
			
			o.setAdate(now);
			o.setAnswer(request.getParameter("answer"));
			o.setStatus(Integer.parseInt(request.getParameter("status")));
			oor.save(o);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
