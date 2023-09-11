package com.trace.trace.qb;

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
@Entity(name = "qaboard_reply")
public class QBReply {

	@Id
	@SequenceGenerator(sequenceName = "qaboard_reply_seq", name = "qrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "qrs")
	@Column(name = "qr_no")
	private Integer qrno;

	@ManyToOne
	@JoinColumn(name = "qr_q_no")
	private QB qaboard;

	@ManyToOne
	@JoinColumn(name = "qr_writer")
	private MemberWriter qbrWriter;
	
	@Column(name = "qr_txt")
	private String txt;
	
	@Column(name = "qr_date")
	private Date date;
	
	@OneToMany(mappedBy = "qbReReply")
	@OrderBy("qrr_no")
	private List<QBReReply> qbReReplys;
}
