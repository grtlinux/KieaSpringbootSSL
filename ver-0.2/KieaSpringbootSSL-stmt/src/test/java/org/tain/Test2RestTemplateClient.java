package org.tain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.tain.domain.Stmt;
import org.tain.utils.CurrentInfo;
import org.tain.utils.SslSkipConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.java.Log;

@Log
public class Test2RestTemplateClient {

	private static boolean flag = true;

	private static final String GET_STMTS_ENDPOINT_URL = "https://localhost:8443/api/stmts";
	private static final String GET_STMT_ENDPOINT_URL = "https://localhost:8443/api/stmts/{id}";
	private static final String CREATE_STMT_ENDPOINT_URL = "https://localhost:8443/api/stmts";
	private static final String UPDATE_STMT_ENDPOINT_URL = "https://localhost:8443/api/stmts/{id}";
	private static final String DELETE_STMT_ENDPOINT_URL = "https://localhost:8443/api/stmts/{id}";
	
	public static void main(String[] args) throws Exception {
		
		SslSkipConfig.skip();
		if (flag) getStmts();
		if (flag) createStmt();
		if (flag) getStmtById();
		if (flag) updateStmt();
		if (flag) deleteStmt();	
	}

	private static RestTemplate restTemplate = new RestTemplate();
	
	private static void getStmts() throws Exception {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		//RestTemplate restTemplate = new RestTemplate();
		
		if (flag) {
			System.out.println("----------------------------------------");
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			
			//RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.exchange(GET_STMTS_ENDPOINT_URL, HttpMethod.GET, entity, String.class);
			System.out.println(">>>>> " + result);
		}
		
		if (flag) {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			System.out.println("----------------------------------------");
			//RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(GET_STMTS_ENDPOINT_URL, HttpMethod.GET, null, String.class);
			String data = response.getBody();
			System.out.println(">>>>> " + data);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			JsonNode jsonNode = objectMapper.readTree(data);
			String test = jsonNode.at("/_embedded/stmts").toPrettyString();
			System.out.println(">>>>> " + test);
			
			List<Stmt> lstStmt = objectMapper.readValue(test, new TypeReference<List<Stmt>>() {});
			for (Stmt stmt : lstStmt) {
				System.out.println(">>>>> " + stmt);
			}
			stopWatch.stop();
			System.out.println(">>>>> " + stopWatch.prettyPrint());
		}
		
		if (!flag) {
			// Error
			System.out.println("----------------------------------------");
			ResponseEntity<Stmt[]> responseEntity = restTemplate.getForEntity(GET_STMTS_ENDPOINT_URL, Stmt[].class);
			String result = responseEntity.getBody().toString();   // HATEOAS
			System.out.println(">>>>> " + result);
		}
	}

	private static void createStmt() {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		//RestTemplate restTemplate = new RestTemplate();
		
		if (flag) {
			Stmt newStmt = Stmt.join(1, 1101, "Hello, world!!", "내용이 좋아요");
			Stmt stmt = restTemplate.postForObject(CREATE_STMT_ENDPOINT_URL, newStmt, Stmt.class);
			System.out.println(">>>>> " + stmt);
		}
	}

	private static void getStmtById() {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		//RestTemplate restTemplate = new RestTemplate();
		
		if (flag) {
			Map<String, String> params = new HashMap<>();
			params.put("id", "1");
			Stmt stmt = restTemplate.getForObject(GET_STMT_ENDPOINT_URL, Stmt.class, params);
			System.out.println(">>>>> " + stmt);
		}
	}

	private static void updateStmt() {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		//RestTemplate restTemplate = new RestTemplate();
		
		if (flag) {
			Map<String, String> params = new HashMap<>();
			params.put("id", "1");
			Stmt updateStmt = Stmt.join(1, 1, "Hello", "강석");
			restTemplate.put(UPDATE_STMT_ENDPOINT_URL, updateStmt, params);
		}
	}

	private static void deleteStmt() {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		//RestTemplate restTemplate = new RestTemplate();
		
		if (flag) {
			Map<String, String> params = new HashMap<>();
			params.put("id", "4");
			restTemplate.delete(DELETE_STMT_ENDPOINT_URL, params);
		}
	}
}
