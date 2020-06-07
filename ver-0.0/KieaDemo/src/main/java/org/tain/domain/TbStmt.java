package org.tain.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TbStmt {

	private Long mstId;
	
	private int seqNo;

	private int groupNo;
	private String groupName;
	
	// TbSubStmt
	//private String[] stmtEn;
	//private String[] stmtKr;
	
	private LocalDateTime createAt = LocalDateTime.now();
}
