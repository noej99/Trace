package com.trace.trace.dataroom;

import java.util.Date;

import com.trace.trace.member.MemberWriter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "DATAROOM_RE_REPLY")
public class DataRoomReReply {
	@Id
	@SequenceGenerator(sequenceName = "dataroom_re_reply_seq", name = "drrrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drrrs")
	@Column(name = "drrr_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "drrr_dr_no")
	private DataRoom drrrDr;
	
//	@Column(name = "drrr_dr_no")
//	private Integer drNo;
	
	@ManyToOne
	@JoinColumn(name = "drrr_drr_no")
	private DataRoomReply drrrDrr;
	
	@ManyToOne
	@JoinColumn(name = "drrr_writer")
	private MemberWriter drrrMw;
//	@Column(name = "drrr_writer")
//	private String writer;
	
	@Column(name = "drrr_txt")
	private String txt;
	
	@Column(name = "drrr_date")
	private Date date;
}
