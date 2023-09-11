package com.trace.trace.admin;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Report {

//	CREATE TABLE REPORT(
//			r_no number(6) PRIMARY KEY,
//			r_reporter varchar2(40 char) NOT NULL,
//			r_b_no number(6),
//			r_b_r_no number(6),
//			r_b_rr_no number(6),
//			r_b_reason varchar2 (150 char),
//			r_cate varchar2(20 char),
//			r_cate2 varchar2(10 char) NOT NULL,
//			CONSTRAINT r_reporter
//			FOREIGN KEY (r_reporter) REFERENCES Member_writer(mw_m_id)
//			ON DELETE CASCADE
//
//			);
	@Id
	@Column(name = "r_no")
	@SequenceGenerator(sequenceName = "report_seq",name = "rs",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rs")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "r_reporter")
	private MemberWriter reporter;
	
	@Column(name = "r_b_no")
	private Integer rbno;
	@Column(name = "r_b_r_no")
	private Integer rbrno;
	@Column(name = "r_b_rr_no")
	private Integer rbrrno;
	@Column(name = "r_b_reason")
	private String reason;
	@Column(name = "r_cate")
	private String cate;
	@Column(name = "r_cate2")
	private String cate2;
	
	
}
