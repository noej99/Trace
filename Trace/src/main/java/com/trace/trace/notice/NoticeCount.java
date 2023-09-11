package com.trace.trace.notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notice_board_count")
public class NoticeCount {
	
	@Id
	@Column(name = "nbc_no")
	private Integer no;
	
	@Column(name = "nbc_view")
	private Integer view;
	
	@Column(name = "nbc_like")
	private Integer like;
}
