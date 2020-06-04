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
@RequestMapping("/kang")
@Log
public class KangController {

	@Autowired
	private StmtRepository stmtRepository;
	
	// url: https://localhost:8443/kang
	@GetMapping("")
	public String index() {
		return "Hello, world!!";
	}
	
	// url: https://localhost:8443/kang/seq?no=707
	@GetMapping("/seq")
	public Stmt findBySeqNo1(@RequestParam(value="no", defaultValue="707") Integer no) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		Stmt stmt = this.stmtRepository.findBySeqNo(no);
		return stmt;
	}
	
	// url: https://localhost:8443/kang/stmt?seqno=707
	@GetMapping("/stmt/{seqno}")
	public Stmt findBySeqNo2(@PathVariable(value="seqno") Integer seqno) {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		return this.stmtRepository.findBySeqNo(seqno);
	}
}
