package org.tain.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "employee")
@Data
@SequenceGenerator(
		name = "emp_seq"
		, sequenceName = "employee_seq"
		, initialValue = 1
		, allocationSize = 1
)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
	@Column(name="id", nullable=false, unique=true, length=15)
	private Long id;
	
	@Column(name = "name", columnDefinition="varchar(1024)")
	private String name;
	
	@Column(name = "desc", columnDefinition="CLOB NOT NULL")
	@Lob
	private String desc;
	
	@Column(name = "dttm")
	private Timestamp dttm;
	
	@Column(name = "credttm")
	private LocalDateTime credttm = LocalDateTime.now();

	@Column(name = "upddttm")
	private LocalDateTime upddttm;
}
