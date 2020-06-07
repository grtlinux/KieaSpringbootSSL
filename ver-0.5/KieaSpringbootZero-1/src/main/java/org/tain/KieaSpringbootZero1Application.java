package org.tain;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Stmt;
import org.tain.object.StmtObject;
import org.tain.properties.EnvTestProperties;
import org.tain.repository.StmtRepository;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaSpringbootZero1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaSpringbootZero1Application.class, args);
	}

	/////////////////////////////////////////////////////////////////////
	
	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (flag) test00();
		if (flag) test01();
		if (flag) test02();
	}

	//@Value("${spring.profiles.active}")
	//@Value("${USER}")                                    // kamgmac
	//@Value("#{systemProperties['USER']}")                // null
	//@Value("#{systemProperties['USER'] ?: 'no user'}")   // no user
	//@Value("Hello, world!!!")
	//@Value("${HOME}")                                    // /Users/kangmac
	//@Value("${KANG:not found..}")                        // not found..
	//@Value("${java.home}")
	//@Value("#{systemProperties['java.home']}")           // the same of the above value
	@Value("${env.data-json-file}")
	private String value;
	
	private void test00() {
		log.info("KANG-20200607 >>>>> " + this.value);
	}

	@Autowired
	private EnvTestProperties envTestProperties;
	
	private void test01() throws Exception {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		for (String server : this.envTestProperties.getServers()) {
			log.info("KANG-20200607 >>>>> " + server);
		}
		log.info("KANG-20200607 >>>>> " + this.envTestProperties.getUser());
	}

	@Autowired
	private StmtRepository stmtRepository;
	
	private void test02() throws Exception {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
		//List<StmtObject> lstStmt = objectMapper.readValue(new File("src/main/resources/1000-2.json"), new TypeReference<List<StmtObject>>() {});
		List<StmtObject> lstStmt = objectMapper.readValue(new File(this.value), new TypeReference<List<StmtObject>>() {});
		for (StmtObject stmt : lstStmt) {
			log.info("KANG-20200607 >>>>> " + stmt);
			this.stmtRepository.save(Stmt.join(stmt.getGroupNo(), stmt.getSeqNo(), stmt.getStmtEn(), stmt.getStmtKr()));
		}
		/*
			objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lstStmt);
			new ObjectMapper()
				.writer()
				.withDefaultPrettyPrinter()
				.writeValueAsString(new HashMap<String, Object>());
		*/
		objectMapper.writeValue(new File("src/main/resources/1000-2.json"), lstStmt);
	}
}
