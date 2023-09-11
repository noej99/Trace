package com.trace.trace.codeboard;

import java.util.Date;
import java.util.List;

import com.trace.trace.member.MemberWriter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "code_board")
public class CodeBoard {
	
	@Id
	@SequenceGenerator(sequenceName = "code_board_seq",name = "cbs",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cbs")
	@Column(name = "cb_no")
	private Integer no;
	
	@Column(name = "cb_title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "cb_writer")
	private MemberWriter writer;
	
	@Column(name = "cb_txt")
	private String txt;
	
	@Column(name = "cb_date")
	private Date date;
	
    @OneToMany(mappedBy = "cbrCN")
    @OrderBy("cbr_no")
    private List<CodeBoardReply> replys;

}
