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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "QABOARD")
public class QB {

	@Id
	@SequenceGenerator(sequenceName = "qaboard_seq", name = "qs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "qs")
	@Column(name = "q_no")
	private Integer no;

	@Column(name = "q_cate")
	private String cate;

	@Column(name = "q_title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "q_writer")
	private MemberWriter qbWriter;

	@Column(name = "q_txt")
	private String txt;

	@Column(name = "q_date")
	private Date date;
	
	@OneToMany(mappedBy = "qaboard")
	@OrderBy("qr_no")
	private List<QBReply> qbReplys;
}
