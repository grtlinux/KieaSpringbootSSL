package org.tain;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SpringClientTest {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (!flag) test01();  // normal
		if (!flag) test02();  // ssl fail
		if (flag) test03();  // ssl
	}

	private static void test01() {
		System.out.println("----------------------------------------");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/test", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}
	
	private static void test02() {
		System.out.println("----------------------------------------");
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/test", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}
	private static void test03() throws Exception {
		System.out.println("----------------------------------------");
		
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
			.loadTrustMaterial(null, acceptingTrustStrategy)
			.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		CloseableHttpClient httpClient = HttpClients.custom()
			.setSSLSocketFactory(csf)
			.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/test", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}
}










