package com.trace.trace.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MemberDAO {
	private SimpleDateFormat sdf;
	private BCryptPasswordEncoder bcpe;
	

	public MemberDAO() {
		bcpe = new BCryptPasswordEncoder();
		sdf = new SimpleDateFormat("YYYY-MM-dd");
	}

	@Autowired
	private MemberRepo mr;
	
	@Autowired
	private MemberLogRepo mlr;
	
	
	@Autowired
	private MemberWriterRepo mwr;
	

	
	public Members getMemberJson(Member m) {
		try {
			List<Member>member=mr.findAll();
			for (Member member2 : member) {
				member2.setAddr(null);
				member2.setBirth(null);
				member2.setIcon(null);
				member2.setPw(null);
				member2.setPw(null);
				member2.setName(null);
				
			}
			Members members=new Members(member);
			return members;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Members getMemberIDNickJson(Member m,HttpServletRequest request) {
		try {
			Member mm=(Member) request.getSession().getAttribute("loginMember");
			List<Member>member=mr.findByIdEquals(mm.getId());
			Members members=new Members(member);
			return members;
			
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		
		}
	}
	


	
	public boolean joinEmailIDCk(String email,String inputid) {
		if (email!=inputid||email==null) {
			inputid="";
			return false;
		}else {
			return true;
		}
	}
	

	public void join(HttpServletRequest request, Member m,MemberWriter mw) {
		try {
			String emailckid=(String) request.getSession().getAttribute("emailuser");
			String id=request.getParameter("email");
			
			joinEmailIDCk(emailckid, id);
			
			int leave = Integer.parseInt(request.getParameter("leave1"));
			String birth1 = request.getParameter("birth1");
			String pw1 = request.getParameter("pw1");
			Date birth = sdf.parse(birth1);
			sdf.format(birth);
			String addr1 = request.getParameter("addr1");
			String addr2 = request.getParameter("addr2");
			String addr3 = request.getParameter("addr3");
			String addr = addr1 + "!" + addr2 + "!" + addr3;
			m.setId(id);
			m.setPw(bcpe.encode(pw1));
			
			m.setLeave(leave);
			m.setBirth(birth);
			m.setAddr(addr);
			mw.setId1(id);
			mw.setNick1(m.getNick());
			mw.setIcon1(m.getIcon());
			if (mr.findById(m.getId()).isEmpty()&&mr.findByNick(m.getNick()).isEmpty()) {
				mr.save(m);
				mwr.save(mw);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public void splitAddr(HttpServletRequest request) {
		Member m=(Member)request.getSession().getAttribute("loginMember");
		String addr=m.getAddr();
		String[]addrs=addr.split("!");
		request.setAttribute("addr1", addrs[0]);
		request.setAttribute("addr2", addrs[1]);
		request.setAttribute("addr3", addrs[2]);
	}
	public void splitbirth(HttpServletRequest request) {
		Member m=(Member)request.getSession().getAttribute("loginMember");
		Date birth=m.getBirth();
		String births=sdf.format(birth);
		String[] births2=births.split("-");
		request.setAttribute("birth1", births2[0]);
		request.setAttribute("birth2", births2[1]);
		request.setAttribute("birth3", births2[2]);
	}
	
	public void updatePw(HttpServletRequest request,Member m) {
		try {
			String id=request.getParameter("email");
			String pw1=request.getParameter("pw1");
			
			m=mr.findById(id).get();
			m.setPw(bcpe.encode(pw1));
			mr.save(m);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(HttpServletRequest request,Member m,MemberWriter mw) {
		try {
			Member dbm=(Member) request.getSession().getAttribute("loginMember");
			m=mr.findById(dbm.getId()).get();
			mw=mwr.findById(dbm.getId()).get();
			
			String pw1=request.getParameter("pw1");
			String nick=request.getParameter("nick");
			String name=request.getParameter("name");
			String icon=request.getParameter("icon");
			String addr1 = request.getParameter("addr1");
			String addr2 = request.getParameter("addr2");
			String addr3 = request.getParameter("addr3");
			
			m.setPw(bcpe.encode(pw1));
			m.setName(name);
			m.setIcon(icon);
			m.setNick(nick);
			m.setAddr(addr1+"!"+addr2+"!"+addr3);
			mw.setId1(m.getId());
			mw.setNick1(m.getNick());
			mw.setIcon1(m.getIcon());
			if (mr.findByNick(m.getNick()).isEmpty()) {
				
				mr.save(m);
				mwr.save(mw);
				request.getSession().setAttribute("loginMember", m);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			
			
			
		}
		
		
		
	}
	public void delete(HttpServletRequest request) {
		try {
			//휴면 계정
			Member m=(Member) request.getSession().getAttribute("loginMember");
			
			m.setLeave(1);
			
			if (mr.save(m)!=null) {
				logout(request);
				islogined(request);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} 
		 
		 
	}
	public void reactivate(HttpServletRequest request) {
		String id=request.getParameter("email");
		List<Member>m=mr.findByIdEquals(id);
		m.get(0).setLeave(0);
		mr.saveAll(m);
		
	}

	public boolean islogined(HttpServletRequest request) {
		Member m = (Member) request.getSession().getAttribute("loginMember");
		if (m != null) {
			
			return true;
		} else {
			return false;
		}

	}

	public void logout(HttpServletRequest request) {
		if (request.getSession().getAttribute("loginMember")!=null) {
			request.getSession().setAttribute("loginMember", null);
			
		}
		
		
	}
	public void setLog(HttpServletRequest request) {
		MemberLog ml=new MemberLog();
		Member m=(Member) request.getSession().getAttribute("loginMember");
		ml.setLogId(m.getId());
		Date now=new Date();
		ml.setLogDate(now);
		mlr.save(ml);
	}
	public void getLog(HttpServletRequest request) {
		Sort s = Sort.by(Sort.Order.desc("logDate"));
		Pageable p = PageRequest.of(0, 50, s);
		Page<MemberLog>ml=mlr.findAll(p);
		
		request.setAttribute("memberlog", ml);
		
		
		
	}
	
	public boolean loginCK(HttpServletRequest request) {
		Member m=(Member)request.getSession().getAttribute("loginMember");
		String pwCk=request.getParameter("pw");
		if (bcpe.matches(pwCk, m.getPw())) {
		
			return true;
		}else {
			return false;
		}
			
		
	}
	
	public void login(HttpServletRequest request, Member inputm) {
		try {
			
			List<Member> dbm = mr.findByIdEqualsAndLeave(inputm.getId(),0);
			if (dbm != null) {
				for (Member member : dbm) {
					if (bcpe.matches(inputm.getPw(), member.getPw())) {
						
						request.getSession().setAttribute("loginMember", member);
						
						request.getSession().setMaxInactiveInterval(10*30*60);
						setLog(request);

					} else {
					}

				}

			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
