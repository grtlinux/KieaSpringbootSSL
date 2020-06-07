package org.tain;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Stmt;
import org.tain.object.StmtObject;
import org.tain.repository.StmtRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaSpringbootZeroApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaSpringbootZeroApplication.class, args);
	}

	///////////////////////////////////////////////////////////////////
	
	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (flag) test01(args);
	}

	@Autowired
	private StmtRepository stmtRepository;
	
	private void test01(String[] args) throws Exception {
		if (flag) {
			ObjectMapper objectMapper = new ObjectMapper();
			List<StmtObject> lstStmt = objectMapper.readValue(new File("src/main/resources/1000-2.json"), new TypeReference<List<StmtObject>>() {});
			for (StmtObject stmt : lstStmt) {
				log.info("KANG-20200606 >>>>> " + stmt);
				this.stmtRepository.save(Stmt.join(stmt.getGroupNo(), stmt.getSeqNo(), stmt.getStmtEn(), stmt.getStmtKr()));
			}
		}
	}
}
