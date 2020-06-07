package org.tain.object;

import lombok.Data;

/**
 * Stmt1000.json -> Stmt
 * 
 * @author kangmac
 *
 */
@Data
public class Stmt {

	private int seqNo;
	private int groupNo;
	private String groupName;
	
	private String[] stmtEn;
	private String[] stmtKr;
}
