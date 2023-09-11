package com.trace.trace.freeboard.reply;

import java.util.Date;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="freeboard_reply_re")
public class FB_ReplyRe {
	@Id
	@SequenceGenerator(sequenceName = "freeboard_reply_re_seq", name = "frrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frrs")
	@Column(name="fbrr_no")
	private Integer fbrr_no; // 대댓글 번호, 시퀸스로 차곡차곡 쌓아짐
	
	@Column(name="fbr_re_text")
	private String re_text;
	
	@Column(name = "fbrr_re_writer")
	private String writer;
	
	@Column(name="fbrr_date")
	private Date date;
	
	@Column(name = "fbrr_post_no") //게시글 번호
	private Integer no;
	
	
	@ManyToOne
	@JoinColumn(name = "fbrr_fbr_no")
	private FB_Reply fbrno;
	
}
