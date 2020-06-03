package org.tain;

import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
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
		if (flag) test01();  // normal
		if (flag) test02();  // ssl SUCCESS
		if (flag) test03();  // ssl SUCCESS
	}

	private static void test01() throws Exception {
		System.out.println("----------------------------------------");
		System.out.println("----------------------------------------");
		System.out.println("----------------------------------------");
		try {
			RestTemplate restTemplate = new RestTemplate();
			//ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/test", HttpMethod.GET, null, String.class);
			ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/test", HttpMethod.GET, null, String.class);
			String data = response.getBody();
			System.out.println(">>>>> " + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void test02() throws Exception {
		System.out.println("----------------------------------------");
		System.out.println("----------------------------------------");
		System.out.println("----------------------------------------");
		sslConfiguration();  // SSL configuration (skip to check SSL)

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/test", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}
	private static void test03() throws Exception {
		System.out.println("----------------------------------------");
		System.out.println("----------------------------------------");
		System.out.println("----------------------------------------");
		
		RestTemplate restTemplate;
		restTemplate = getRestTemplate();
		//restTemplate = getRestTemplateBypassingHostNameVerification();
		ResponseEntity<String> response = restTemplate.exchange("https://localhost:8443/test", HttpMethod.GET, null, String.class);
		String data = response.getBody();
		System.out.println(">>>>> " + data);
	}
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	// SSL SUCCESS
	private static void sslConfiguration() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}
		};
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}
	
	// SSL SUCCESS
	private static RestTemplate getRestTemplate() throws Exception {
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
		
		return new RestTemplate(requestFactory);
	}
	
	// SSL FAIL
	public static RestTemplate getRestTemplateBypassingHostNameVerification() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
	}
	
}










