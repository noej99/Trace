package com.trace.trace.member;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
	@Id
	@Column(name = "m_id")
	private String id;
	@Column(name = "m_pw")
	private String pw;
	@Column(name = "m_name")
	private String name;
	@Column(name = "m_nick")
	private String nick;
	@Column(name = "m_birth")
	private Date birth;
	@Column(name = "m_addr")
	private String addr;
	@Column(name = "m_icon")
	private String icon;
	@Column(name = "m_point")
	private Integer point;
	@Column(name = "m_leave")
	private Integer leave;

}
