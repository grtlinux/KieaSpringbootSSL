package org.tain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tain.domain.Stmt;
import org.tain.repository.StmtRepository;
import org.tain.utils.CurrentInfo;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/stmts")
@Log
public class StmtController {

	@Autowired
	private StmtRepository stmtRepository;
	
	@GetMapping("/seq")
	public Stmt findBySeqNo(@RequestParam(value="no", defaultValue="707") Integer no) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		Stmt stmt = this.stmtRepository.findBySeqNo(no);
		return stmt;
	}
	
	@GetMapping("/stmt")
	public Stmt findBySeqNo(@RequestParam("seqno") int seqno) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		return this.stmtRepository.findBySeqNo(seqno);
	}
}
