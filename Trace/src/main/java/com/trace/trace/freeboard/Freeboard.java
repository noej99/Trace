package com.trace.trace.freeboard;

import java.util.Date;

import com.trace.trace.member.MemberWriter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="freeboard")
public class Freeboard {
	
	@Id
	@SequenceGenerator(sequenceName = "freeboard_post_seq", name = "fps", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fps")
	@Column(name="fb_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name="fb_writer")
	private MemberWriter writer;
	
	@Column(name="fb_contents")
	private String contents;
	
	@Column(name="fb_date")
	private Date date;
	
	@Column(name="fb_view")
	private Integer view;
	

}
