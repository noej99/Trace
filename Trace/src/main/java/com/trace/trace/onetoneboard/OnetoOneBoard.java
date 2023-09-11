package com.trace.trace.onetoneboard;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "onetoone_board")
public class OnetoOneBoard {
	
	
//	CREATE TABLE onetoone_board (
//		    ob_no number(6) NOT NULL,
//		    ob_writer varchar2(40 char) PRIMARY KEY,
//		    ob_title varchar2(150 char) NOT NULL,
//		    ob_category varchar2(30 char) NOT NULL,
//		    ob_contents varchar2(2000 char) NOT NULL,
//		    ob_status number(1) NOT NULL,
//		    CONSTRAINT ob_writer
//		        FOREIGN KEY (ob_writer) REFERENCES Member_writer(mw_m_id) 
//		        ON DELETE CASCADE
//		);
	@Id
	@SequenceGenerator(sequenceName = "ob_no_seq",name = "ons",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ons")
	@Column(name = "ob_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "ob_writer")
	private MemberWriter writer;
	
	@Column(name = "ob_title")
	private String title;
	@Column(name = "ob_category")
	private String cate;
	@Column(name = "ob_contents")
	private String contents;
	@Column(name = "ob_answer")
	private String answer;

	@Column(name = "ob_answerdate")
 	private Date adate;
	@Column(name="ob_date")
	private Date date;

	@Column(name = "ob_status")
	private Integer status;
	
	
}
