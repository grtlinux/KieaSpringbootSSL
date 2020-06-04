package org.tain;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.tain.utils.CurrentInfo;
import org.tain.utils.SslSkipConfig;

import lombok.extern.java.Log;

@Log
public class TestRestTemplateClient {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (!flag) test01();
		if (flag) test02();
		if (flag) test03();
	}

	private static void test01() {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());

		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/test", HttpMethod.GET, null, String.class);
			String data = response.getBody();
			System.out.println(">>>>> " + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void test02() throws Exception {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		SslSkipConfig.skip();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/stmts", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}

	private static void test03() throws Exception {
		log.info("KANG-20200604 >>>>> " + CurrentInfo.get());
		
		RestTemplate restTemplate = SslSkipConfig.getRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/stmts/1", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}
}
