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
@Entity(name = "dataroom_file")
public class DataRoomFile {
	@Id
	@SequenceGenerator(sequenceName = "dataroom_file_seq", name = "drfs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drfs")
	@Column(name = "drf_no")
	private Integer no;
	
	@Column(name = "drf_dr_no")
	private Integer drNo;
	
	@Column(name = "drf_file")
	private String file;
}
