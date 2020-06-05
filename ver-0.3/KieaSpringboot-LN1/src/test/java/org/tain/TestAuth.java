package org.tain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestAuth {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		SkipSSLConfig.skip();
		if (flag) test01();
		
		if (!flag) exec01();
	}

	private static final String POST_AUTH_ENDPOINT_URL = "https://test-public.lightnetapis.io/v1/auth";
	
	private static void test01() throws Exception {
		if (flag) {
			System.out.println("----------------------------------------");
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.exchange(POST_AUTH_ENDPOINT_URL, HttpMethod.GET, entity, String.class);
			System.out.println(">>>>> " + result);
		}
	}
	
	// REF: https://vmpo.tistory.com/27
	//@RestController
	@GetMapping("/abcdef")
	private static String exec01() throws Exception {
		if (flag) {
			Map<String, Object> result = new HashMap<>();
			String jsonInString = "";
			
			try {
				HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
				factory.setConnectTimeout(5000);
				factory.setReadTimeout(5000);
				RestTemplate restTemplate = new RestTemplate(factory);
				
				HttpHeaders header = new HttpHeaders();
				HttpEntity<?> entity = new HttpEntity<>(header);
				
				String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
				UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"key=430156241533f1d058c603178cc3ca0e&targetDt=20120101").build();
				
				ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
				result.put("statusCode", resultMap.getStatusCodeValue());
				result.put("header", resultMap.getHeaders());
				result.put("body", resultMap.getBody());
				
				LinkedHashMap lm = (LinkedHashMap)resultMap.getBody().get("boxOfficeResult");
				List<Map> dboxoffList = (ArrayList<Map>)lm.get("dailyBoxOfficeList");
				LinkedHashMap mnList = new LinkedHashMap<>();
				for (Map obj : dboxoffList) {
					mnList.put(obj.get("rnum"),obj.get("movieNm"));
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				jsonInString = objectMapper.writeValueAsString(resultMap.getBody());
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				result.put("statusCode", e.getRawStatusCode());
				result.put("body", e.getStatusText());
				System.out.println("----------------");
				System.out.println(e.toString());
			} catch (Exception e) {
				result.put("statusCode", "9999");
				result.put("body", "exception Error");
				System.out.println(e.toString());
			}
			
			return jsonInString;
		}
		
		if (!flag) {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000);
			factory.setReadTimeout(5000);
			RestTemplate restTemplate = new RestTemplate(factory);
			
			HttpHeaders header = new HttpHeaders();
			HttpEntity<?> entity = new HttpEntity<>(header);
			
			String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
			UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"key=430156241533f1d058c603178cc3ca0e&targetDt=20120101").build();

			ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
			int statusCodeValue = resultMap.getStatusCodeValue();
			HttpStatus httpStatus = resultMap.getStatusCode();
			HttpHeaders httpHeaders = resultMap.getHeaders();
			Map<?, ?> body = resultMap.getBody();
			Map<?, ?> office = (LinkedHashMap<?,?>) body.get("Office");
			
			// jsonInString = mapper.writeValueAsString(mapObject);
		}
		
		return null;
	}
	
	
	// CustomRestTemplate
	public RestTemplate getCustomRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(2000);
		httpRequestFactory.setReadTimeout(3000);
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(200)
				.setMaxConnPerRoute(20)
				.build();
		httpRequestFactory.setHttpClient(httpClient);
		return new RestTemplate(httpRequestFactory);
	}
}
