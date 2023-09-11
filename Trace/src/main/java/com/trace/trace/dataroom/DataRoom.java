package com.trace.trace.dataroom;

import java.util.Date;
import java.util.List;

import com.trace.trace.member.MemberWriter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "dataroom")
public class DataRoom {
	@Id
	@SequenceGenerator(sequenceName = "dataroom_seq", name = "drs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drs")
	@Column(name = "dr_no")
	private Integer no;
	
	@Column(name = "dr_title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "dr_writer")
	private MemberWriter drMw;
//	@Column(name = "dr_writer")
//	private String writer;

	@Column(name = "dr_content")
	private String content;
	
	@Column(name = "dr_date")
	private Date date;
	
	@OneToMany(mappedBy = "drrDr") 
	@OrderBy("drr_no")
	private List<DataRoomReply> replys;
	
	@OneToMany(mappedBy = "drrrDr") 
	@OrderBy("drrr_drr_no, drrr_no")
	private List<DataRoomReReply> reReplys;
	
//	@OneToMany(mappedBy = "drlcDr")
//	private List<DataRoomLikeCheck> drlcs;
}
