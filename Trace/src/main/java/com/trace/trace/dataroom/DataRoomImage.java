package com.trace.trace.dataroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "dataroom_image")
public class DataRoomImage {
	@Id
	@SequenceGenerator(sequenceName = "dataroom_image_seq", name = "dris", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dris")
	@Column(name = "dri_no")
	private Integer no;
	
	@Column(name = "dri_dr_no")
	private Integer drNo;
	
	@Column(name = "dri_file")
	private String file;
}
