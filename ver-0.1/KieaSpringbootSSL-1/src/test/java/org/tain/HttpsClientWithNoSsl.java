package org.tain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsClientWithNoSsl {

	public static void main(String[] args) {
		String urlStr = "https://localhost:8443/test";
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
}
