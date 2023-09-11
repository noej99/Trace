package com.trace.trace.notice;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="notice_board")
public class Notice {
	@Id
	@SequenceGenerator(sequenceName = "nb_seq", name = "nns", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nns")
	@Column(name = "nb_no")
	private Integer no;
	
	@Column(name = "nb_title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "nb_writer")
	private MemberWriter writer;
	
	@Column(name = "nb_contents")
	private String contents;
	
	@Column(name = "nb_date")
	private Date date;
	
	@Column(name = "nb_category")
	private String category;
}
