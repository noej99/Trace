package com.trace.trace.qb;

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
@Entity(name = "qaboard_like_check")
public class QBLikeCheck {
	
	@Id
	@SequenceGenerator(sequenceName = "qaboard_like_check_seq", name = "qlcs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator =  "qlcs")
	private Integer no;
	
	@Column(name = "qlc_no")
	private Integer qlcno;
	
	@Column(name = "qlc_id")
	private String qlcId;
	
	@Column(name = "qlc_check")
	private Integer check;

}
