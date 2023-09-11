package com.trace.trace.qb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "qaboard_count")
public class QBCount {

	@Id
	@Column(name = "qc_no")
	private Integer no;
	
	@Column(name = "qc_like")
	private Integer like;
	
	@Column(name = "qc_view")
	private Integer view;
	
}
