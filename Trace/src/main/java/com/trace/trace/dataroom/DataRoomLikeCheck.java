package com.trace.trace.dataroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "DATAROOM_LIKE_CHECK")
public class DataRoomLikeCheck {
	
	@Id
	@Column(name = "drlc_dr_no")
	private Integer drNo;
	
	@Column(name = "drlc_id")
	private String lcId;

//	@ManyToOne
//	@JoinColumn(name = "drlc_dr_no")
//	private DataRoom drlcDr;
	
//	@ManyToOne
//	@JoinColumn(name = "drlc_id")
//	private MemberWriter drlcMw;
}
