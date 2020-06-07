package org.tain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tain.domain.Stmt;
import org.tain.repository.StmtRepository;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@RestController
@RequestMapping(value = "/stmts")
@Log
public class StmtController {

	@Autowired
	private StmtRepository stmtRepository;
	
	// url: https://localhost:8443/stmts/stmt?seqno=707
	@GetMapping("/stmt")
	public Stmt findBySeqNo1(@RequestParam(value="seqno", defaultValue="707") Integer seqno) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		Stmt stmt = this.stmtRepository.findBySeqNo(seqno);
		return stmt;
	}
	
	// url: https://localhost:8443/stmts/stmt/707
	@GetMapping("/stmt/{seqno}")
	public Stmt findBySeqNo2(@PathVariable(value="seqno") Integer seqno) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		return this.stmtRepository.findBySeqNo(seqno);
	}
}
