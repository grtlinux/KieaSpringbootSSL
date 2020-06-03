package org.tain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.client.RestTemplate;

public class HttpsClientWithNoSsl {

	public static void main(String[] args) { 
		String urlStr = "https://127.0.0.1:8443/test"; 
		StringBuffer sb = new StringBuffer(); 
		
		try { 
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
			
			URL url = new URL(urlStr); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			InputStreamReader in = new InputStreamReader((InputStream) conn.getContent()); 
			BufferedReader br = new BufferedReader(in); 
			
			String line; 
			while ((line = br.readLine()) != null) { 
				sb.append(line).append("\n"); 
			} 
			System.out.println(sb.toString()); 
			
			br.close(); 
			in.close(); 
			conn.disconnect(); 
		} catch (Exception e) { 
			System.out.println(e.toString()); 
		} 
	}
	
	public RestTemplate getRestTemplateBypassingHostNameVerification() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
	       HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	       requestFactory.setHttpClient(httpClient);
	       return new RestTemplate(requestFactory);
	}
	
	public RestTemplate restTemplate2() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
 
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
 
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
 
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
 
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
 
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
	
	/*
	public WebClient createWebClient() throws SSLException {
	    SslContext sslContext = SslContextBuilder
	            .forClient()
	            .trustManager(InsecureTrustManagerFactory.INSTANCE)
	            .build();
	    ClientHttpConnector httpConnector = HttpClient.create().secure(t -> t.sslContext(sslContext) )
	    return WebClient.builder().clientConnector(httpConnector).build();
	}
	*/
}








