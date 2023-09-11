package com.trace.trace.codeboard;

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
@Entity(name = "code_board_reply")
public class CodeBoardReply {
	
	@Id
	@SequenceGenerator(sequenceName = "code_board_reply_seq",name = "cbrs",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cbrs")
	@Column(name = "cbr_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "cbr_cb_no")
	private CodeBoard cbrCN;
	
	@ManyToOne
	@JoinColumn(name = "cbr_writer")
	private MemberWriter cbrWriter;
	
	@Column(name = "cbr_txt")
	private String txt;
	
	@Column(name = "cbr_date")
	private Date date;

}
