package com.trace.trace.freeboard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "FREEBOARD_COUNT")
public class FreeboardLike {
	
	@Id
	@SequenceGenerator(sequenceName = "freeboard_like_seq", name = "fls", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fls")
	@Column(name="fbc_no")
	private Integer likeNo;

	@Column(name = "fbc_m_id")
	private String writer;
	
	@Column(name = "fbc_fb_no")
	private Integer no;
}
