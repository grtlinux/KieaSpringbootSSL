package org.tain;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Board;
import org.tain.domain.Stmt;
import org.tain.domain.User;
import org.tain.domain.enums.BoardType;
import org.tain.domain.enums.SocialType;
import org.tain.object.StmtObject;
import org.tain.properties.EnvTestProperties;
import org.tain.repository.BoardRepository;
import org.tain.repository.StmtRepository;
import org.tain.repository.UserRepository;
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
		
		if (flag) test03();
	}

	/////////////////////////////////////////////////////////////////////
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

	/////////////////////////////////////////////////////////////////////
	@Autowired
	private EnvTestProperties envTestProperties;
	
	private void test01() throws Exception {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		for (String server : this.envTestProperties.getServers()) {
			log.info("KANG-20200607 >>>>> " + server);
		}
		log.info("KANG-20200607 >>>>> " + this.envTestProperties.getUser());
	}

	/////////////////////////////////////////////////////////////////////
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
	
	/////////////////////////////////////////////////////////////////////
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	private void test03() {
		log.info("KANG-20200607 >>>>> " + CurrentInfo.get());
		User user = this.userRepository.save(User.builder()
				.name("kiea")
				.password("kieapass")
				.email("kiea@email.com")
				.principal("ADMIN")
				.socialType(SocialType.GOOGLE)
				.createdDate(LocalDateTime.now())
				.build()
		);
		
		IntStream.rangeClosed(1, 500).forEach(index ->
				this.boardRepository.save(Board.builder()
						.title("제목입니다." + index)
						.subTitle("부제목입니다." + index)
						.content("콘텐츠-내용입니다.\n" + UUID.randomUUID().toString())
						.boardType(BoardType.free)
						.createdDate(LocalDateTime.now())
						.updatedDate(LocalDateTime.now())
						.user(user)
						.build()
				)
		);
	}
}
