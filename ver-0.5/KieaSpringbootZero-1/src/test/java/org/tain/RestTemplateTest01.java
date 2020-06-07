package org.tain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.SkipSSLConfig;

import lombok.extern.java.Log;

@Log
public class RestTemplateTest01 {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (flag) stmts_get_one();
		if (flag) stmts_get_list();
	}

	private static void stmts_get_one() throws Exception {
		if (flag) {
			String endpoint = "https://localhost:8443/api/stmts/3";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			log.info("KANG-20200607 >>>>> " + response);
		}
	}

	private static void stmts_get_list() throws Exception {
		if (flag) {
			String endpoint = "https://localhost:8443/api/stmts";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.queryParam("name", "강석")
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			log.info("KANG-20200607 >>>>> " + response);
		}
	}
}
