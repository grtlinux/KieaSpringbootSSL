package org.tain.domain;

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
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Data
@SequenceGenerator(
		name = "member_seq"
		, sequenceName = "member_seq"
		, initialValue = 100
		, allocationSize = 1
)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length=1024)
	private String name;
	
	@Column(name = "desc")
	@Lob
	private String desc;
	
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();
	
	private Member(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	public static Member join(@NonNull String name, @NonNull String desc) {
		return new Member(name, desc);
	}
}
