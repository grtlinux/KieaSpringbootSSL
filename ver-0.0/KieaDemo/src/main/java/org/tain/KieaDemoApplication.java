package org.tain;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.JsonData;
import org.tain.domain.Member;
import org.tain.domain.Stmt;
import org.tain.object.JsonStmt;
import org.tain.repository.MemberRepository;
import org.tain.repository.StmtRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaDemoApplication implements CommandLineRunner {

	private static boolean flag = true;
	
	public static void main(String[] args) {
		//System.setProperty("user.timezone", "GMT+09:00");
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+09:00"));
		System.out.println("KIEA (ver.2020.01.20.001) >>>>> date = " + new Date());
		
		SpringApplication.run(KieaDemoApplication.class, args);
	}
	
	@Autowired
	private MemberRepository repo;
	
	@Autowired
	private StmtRepository stmtRepo;
	
	public void run(String... args) throws Exception {
		if (!flag) test01();
		if (flag) test02();
		if (!flag) test03();
		if (!flag) test04();
	}
	
	private void test01() throws Exception {
		this.repo.save(Member.join("name 1", "description 1"));
		this.repo.save(Member.join("name 2", "description 2"));
		this.repo.save(Member.join("name 3", "description 3"));
		this.repo.save(Member.join("name 4", "description 4"));
		this.repo.save(Member.join("name 5", "description 5"));
		this.repo.save(Member.join("name 6", "description 6"));
		this.repo.save(Member.join("name 7", "description 7"));
	}
	
	private void test02() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		List<JsonData> lstJsonData = objectMapper.readValue(new File("src/main/resources/jsonData.json"), new TypeReference<List<JsonData>>() {});
		for (JsonData jsonData : lstJsonData) {
			System.out.println(">>>>> " + jsonData);
			this.repo.save(Member.join(jsonData.getName(), jsonData.getDesc()));
		}
	}
	
	private final String USER_AGENT = "Mozilla/5.0";
	private final String PROXY_IP = "192.168.0.1";
	private final int PROXY_PORT = 8888;

	private void test03() throws Exception {
		if (!flag) {
			System.out.println("================== GET ==================");
			//sendGet("http://localhost:8080/");
			//sendGet("https://www.naver.com/");
			//sendGet("https://github.com/");
			sendGet("https://test-public.lightnetapis.io/v1/auth");
		}
		
		if (flag) {
			System.out.println("================== POST ==================");
			//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
			//sendPost("https://github.com/", urlParameters);
			sendPost("https://test-public.lightnetapis.io/v1/auth", "{"
					+ " \"clientId\": \"pkey_tUsjZ1aL8UhvJnNibssfEGo6Y4MhSzXT\","
					+ " \"secret\": \"skey_D1ZL5MW4bKW7clFW2Vz3jH8sm2k7FUfWiu5wh1aL8Uivo6RMNOa74wxfSYo5ylmk\""
					+ "}");
		}
		
		if (!flag) {
			System.out.println("================== PROXY GET ==================");
			//sendGet("http://localhost:8080/");
			//sendGet("https://www.naver.com/");
			sendProxyGet("https://github.com/");
		}
		
		if (!flag) {
			System.out.println("================== PROXY POST ==================");
			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
			sendProxyPost("https://github.com/", urlParameters);
		}
	}
	
	// HTTP GET request
	private void sendGet(String targetUrl) throws Exception {
		if (flag) System.out.printf(">>>>> GET: %s\n", targetUrl);
		
		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("GET");  // optional default is GET
		con.setRequestProperty("User-Agent", USER_AGENT);  // add request header
		
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		
		// print result
		System.out.println("HTTP response code: " + responseCode);
		System.out.println("HTTP body: " + response.toString());
	}
	
	// HTTP POST request
	private void sendPost(String targetUrl, String parameters) throws Exception {
		if (flag) System.out.printf(">>>>> POST: %s %s\n", targetUrl, parameters);
		
		URL url = new URL(targetUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		
		con.setRequestMethod("POST");  // optional default is POST
		//con.setRequestProperty("User-Agent", USER_AGENT);  // add request header
		
		con.setRequestProperty("Content-Type", "application/json");
		//con.setRequestProperty("Host", "lightnet-dev.apigee.net");
		
		con.setDoOutput(true);  // POST 파라미터 전달을 위한 설정
		
		// Send POST request
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		
		// print result
		System.out.println("HTTP response code: " + responseCode);
		System.out.println("HTTP body: " + response.toString());
	}
	
	private Proxy initProxy(String ip, int port) {
		return new Proxy(Proxy.Type.HTTP,new InetSocketAddress(ip, port));
	}
	
	// HTTP GET request by Proxy
	private void sendProxyGet(String targetUrl) throws Exception {
		if (flag) System.out.printf(">>>>> PROXY GET: %s\n", targetUrl);
		
		Proxy proxy = initProxy(PROXY_IP, PROXY_PORT);
		
		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
		
		con.setRequestMethod("GET");  // optional default is GET
		con.setRequestProperty("User-Agent", USER_AGENT);  // add request header
		
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		
		// print result
		System.out.println("HTTP response code: " + responseCode);
		System.out.println("HTTP body: " + response.toString());
	}
	
	// HTTP POST request by Proxy
	private void sendProxyPost(String targetUrl, String parameters) throws Exception {
		if (flag) System.out.printf(">>>>> PROXY POST: %s %s\n", targetUrl, parameters);
		
		Proxy proxy = initProxy(PROXY_IP, PROXY_PORT);
		
		URL url = new URL(targetUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection(proxy);
		
		con.setRequestMethod("POST");  // optional default is POST
		con.setRequestProperty("User-Agent", USER_AGENT);  // add request header
		con.setDoOutput(true);  // POST 파라미터 전달을 위한 설정
		
		// Send POST request
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		
		// print result
		System.out.println("HTTP response code: " + responseCode);
		System.out.println("HTTP body: " + response.toString());
	}
	
	private void test04() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		List<JsonStmt> lstStmt = objectMapper.readValue(new File("src/main/resources/1000-2.json"), new TypeReference<List<JsonStmt>>() {});
		for (JsonStmt stmt : lstStmt) {
			log.info("KANG-20200522 >>>>> " + stmt);
			this.stmtRepo.save(Stmt.join(stmt.getGroupNo(), stmt.getSeqNo(), stmt.getStmtEn(), stmt.getStmtKr()));
		}
	}
}
