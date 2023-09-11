package com.trace.trace.member;

import java.util.Date;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "member_log")
public class MemberLog {
	
	@Id
	@Column(name = "ml_logno")
	@SequenceGenerator(sequenceName = "member_log_seq",name = "mls",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mls")
	private Integer log;
	
	
	
	
	@Column(name = "ml_m_id")
	private String logId;
	
	
	
	
	@Column(name = "ml_m_date")
	private Date logDate;
	
	
	
	
	
	

}
