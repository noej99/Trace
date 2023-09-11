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
@Entity(name = "dataroom_count")
public class DataRoomCount {

	@Id
	@Column(name = "drc_no")
	private Integer no;

	@Column(name = "drc_view")
	private Integer view;

	@Column(name = "drc_like")
	private Integer like;
}
