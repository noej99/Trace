package com.trace.trace.member;

import java.util.List;

import com.trace.trace.admin.Report;
import com.trace.trace.dataroom.DataRoom;
import com.trace.trace.dataroom.DataRoomReReply;
import com.trace.trace.dataroom.DataRoomReply;
import com.trace.trace.freeboard.Freeboard;
import com.trace.trace.notice.Notice;
import com.trace.trace.onetoneboard.OnetoOneBoard;
import com.trace.trace.qb.QB;
import com.trace.trace.qb.QBReReply;
import com.trace.trace.qb.QBReply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Member_Writer")
public class MemberWriter {
	@Id
	@Column(name="mw_m_id")
	private String id1;
	
	@Column(name="mw_m_nick")
	private String nick1;
	
	@Column(name="mw_m_icon")
	private String icon1;
	
	@OneToMany(mappedBy = "drMw")
	private List<DataRoom> drs;

	@OneToMany(mappedBy = "drrMw")
	private List<DataRoomReply> drrs;

	@OneToMany(mappedBy = "drrrMw")
	private List<DataRoomReReply> drrrs;
	
	@OneToMany(mappedBy = "writer")
	private List<Notice> nbs;
	
	@OneToMany(mappedBy = "writer")
	private List<OnetoOneBoard> obs;
	
	@OneToMany(mappedBy = "qbWriter")
	private List<QB> qbs;
	
	@OneToMany(mappedBy = "qbrWriter")
	private List<QBReply> qbr;

	@OneToMany(mappedBy = "qbrrWriter")
	private List<QBReReply> qbrr;
	
	@OneToMany(mappedBy = "reporter")
	private List<Report> rps;
	
	@OneToMany(mappedBy = "writer")
	private List<Freeboard>fbs;
	
	
}
