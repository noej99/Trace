package com.trace.trace.dataroom;

import java.util.Date;
import java.util.List;

import com.trace.trace.member.MemberWriter;

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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "DATAROOM_REPLY")
public class DataRoomReply {
	@Id
	@SequenceGenerator(sequenceName = "dataroom_reply_seq", name = "drrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drrs")
	@Column(name = "drr_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "drr_dr_no")
	private DataRoom drrDr;
//	@Column(name = "drr_dr_no")
//	private Integer drNo;

	@ManyToOne
	@JoinColumn(name = "drr_writer")
	private MemberWriter drrMw;
//	@Column(name = "drr_writer")
//	private String writer;

	@Column(name = "drr_txt")
	private String txt;

	@Column(name = "drr_date")
	private Date date;
	
	@OneToMany(mappedBy = "drrrDrr")
	@OrderBy("drrr_no")
	private List<DataRoomReReply> reReplys;
}
