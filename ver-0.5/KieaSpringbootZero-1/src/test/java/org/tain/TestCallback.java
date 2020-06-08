package org.tain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.tain.utils.CurrentInfo;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestCallback {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		
		SkipSSLConfig.skip();

		if (flag) callback();
	}

	/*
	 * POST: http://localhost:8080/v1/remittances.callback
	 */
	private static final String POST_CALLBACK_ENDPOINT_URL = "http://localhost:8080/v1/remittances.callback";
	
	private static void callback() throws Exception {
		if (flag) {
			System.out.println("\n\n############### (" + CurrentInfo.get() + ") ###########");
			
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			StringBuffer sb = new StringBuffer();
			sb.append("{\n");
			sb.append("  \"counterpartyTransactionId\": \"82622468196393\",\n");
			sb.append("  \"reason\": \"remit completed\",\n");
			sb.append("  \"sourceOperatorCode\": \"airpay\",\n");
			sb.append("  \"status\": \"RECEIVED\",\n");
			sb.append("  \"transactionId\": \"702c74a5-ecea-4545-ae23-9afa9d8b73d1\"\n");
			sb.append("}\n");

			HttpEntity<String> httpEntity = new HttpEntity<>(sb.toString(), headers);
			
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.postForObject(POST_CALLBACK_ENDPOINT_URL, httpEntity, String.class);
			
			System.out.println(">>>>> response: " + response);
			
			if (flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response);
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
	}
}
