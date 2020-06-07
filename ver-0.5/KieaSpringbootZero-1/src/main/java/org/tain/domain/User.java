package org.tain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.tain.domain.enums.SocialType;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@SequenceGenerator(name = "user_seq"
	, sequenceName = "user_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class User {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column
	private String principal;
	
	@Column
	@Enumerated(EnumType.STRING)
	private SocialType socialType;
	
	@Column
	private LocalDateTime createdDate;
	
	@Column
	private LocalDateTime updatedDate;
	
	@Builder
	public User(
			String name,
			String password,
			String email,
			String principal,
			SocialType socialType,
			LocalDateTime createdDate,
			LocalDateTime updatedDate
			) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.principal = principal;
		this.socialType = socialType;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
