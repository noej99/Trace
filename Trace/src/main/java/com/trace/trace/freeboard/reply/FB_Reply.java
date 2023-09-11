package com.trace.trace.freeboard.reply;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="freeboard_reply")
public class FB_Reply {
	
	@Id
	@SequenceGenerator(sequenceName = "freeboard_reply_seq", name = "frs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frs")
	@Column(name="fbr_no")
	private Integer fbr_no; // 댓글 번호, 시퀸스로 차곡차곡 쌓아짐
	
	@Column(name="fbr_text")
	private String text;
	
	@Column(name = "fbr_writer")
	private String writer;
	
	@Column(name="fbr_date")
	private Date date;
	
	@Column(name = "fbr_fb_no") //게시글 번호를 나타내는것
	private Integer no; //Freeboard get메소드를 같이 쓰기 때문에 Freeboard 멤버변수와 같은 표기
	
	@OneToMany(mappedBy = "fbrno")
	@OrderBy("fbrr_date")
	private List<FB_ReplyRe> re_replys;
	

}
