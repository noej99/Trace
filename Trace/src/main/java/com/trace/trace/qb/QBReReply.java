package com.trace.trace.qb;

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
@Entity(name = "qaboard_rereply")
public class QBReReply {

	@Id
	@SequenceGenerator(sequenceName = "qaboard_rereply_seq", name = "qrrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "qrrs")
	@Column(name = "qrr_no")
	private Integer no;

	@ManyToOne
	@JoinColumn(name = "qrr_qr_no")
	private QBReply qbReReply;

	@ManyToOne
	@JoinColumn(name = "qrr_writer")
	private MemberWriter qbrrWriter;

	@Column(name = "qrr_txt")
	private String txt;

	@Column(name = "qrr_date")
	private Date date;
	
}
