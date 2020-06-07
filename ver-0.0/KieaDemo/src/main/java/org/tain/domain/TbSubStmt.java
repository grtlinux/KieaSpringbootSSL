package org.tain.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TbSubStmt {

	private Long id;
	private Long mstId;
	
	private String stmtEn;
	private String stmtKr;
	
	private LocalDateTime createAt = LocalDateTime.now();
}
