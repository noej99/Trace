package com.trace.trace.notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notice_like_check")
public class NoticeLikeCheck {
	
	@Id
	@SequenceGenerator(sequenceName = "nl_n_seq", name = "noSeulmo", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noSeulmo")
	@Column(name = "nl_n")
	private Integer n;
	
	@Column(name = "nl_no")
	private Integer no;

	@Column(name = "nl_user")
	private String user;
	
	@Column(name = "nl_check")
	private Integer check;
}
