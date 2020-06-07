package org.tain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.SkipSSLConfig;

public class TestSslWithRestTemplate {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (flag) test01();
	}

	private static String STMTS_GET_LIST_ENDPOINT = "https://localhost:8443/api/stmts";
	
	private static void test01() throws Exception {
		if (flag) {
			SkipSSLConfig.skip();
			
			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
			parameters.add("kang", "강석");

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			//HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(STMTS_GET_LIST_ENDPOINT)
					.queryParam("name", "강석")
					.build(false);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			System.out.println(">>>> response " + response);
		}
	}
}
