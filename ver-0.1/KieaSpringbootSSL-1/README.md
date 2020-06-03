

- Chrome이 자체 서명 된 로컬 호스트 인증서를 허용하도록하기: chrome url입력에 넣어서 실행하면 보안화면이 뜨고 '허용' 처리 후 chrome 재시작

- url입력: chrome://flags/#allow-insecure-localhost

- $ curl -k https://localhost:8443/test

```
dependencies {
	implementation 'org.apache.httpcomponents:httpclient'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}

```



```
1. Generate server key and self signed server certificate
	keytool -genkey -alias serverkey -keyalg RSA -storetype PKCS12 -keystore serverkeystore.p12 -ext SAN=dns:abc.com,dns:localhost,ip:127.0.0.1
	
2. Generate client key and self signed client certificate
	keytool -genkey -alias clientkey -keyalg RSA -storetype PKCS12 -keystore clientkeystore.p12 -ext SAN=dns:def.com,dns:localhost,ip:127.0.0.1
	
3. Export the server certificate
	keytool -export -alias serverkey -file servercert.cer -keystore serverkeystore.p12
	
4. Export the client certificate
	keytool -export -alias clientkey -file clientcert.cer -keystore clientkeystore.p12
	
5. Import cert to $JAVA_HOME/jre/lib/security
	sudo keytool -import -trustcacerts -alias localhost -file localhost.crt -keystore $JAVA_HOME/jre/lib/security/cacerts
	
	
server:
  port: 8443
  ssl:
    enabled: true
    key-store: classpath:serverkeystore.p12
    key-store-password: server
    key-alias: serverkey
```

```
$ keytool -genkey -alias serverkey -keyalg RSA -storetype PKCS12 -keystore serverkeystore.p12 -ext SAN=dns:localhost,ip:127.0.0.1
	키 저장소 비밀번호 입력:
	새 비밀번호 다시 입력:
	이름과 성을 입력하십시오.
	  [Unknown]:  Kiea Seok Kang
	조직 단위 이름을 입력하십시오.
	  [Unknown]:  Dev
	조직 이름을 입력하십시오.
	  [Unknown]:  TAIN
	구/군/시 이름을 입력하십시오?
	  [Unknown]:  Seoul
	시/도 이름을 입력하십시오.
	  [Unknown]:  Korea
	이 조직의 두 자리 국가 코드를 입력하십시오.
	  [Unknown]:  KR
	CN=Kiea Seok Kang, OU=Dev, O=TAIN, L=Seoul, ST=Korea, C=KR이(가) 맞습니까?
	  [아니오]:  y

$ ls -al serverkeystore.p12
```


```
HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
});
```

```
@Bean
public RestTemplate restTemplate() 
                throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
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
```

```
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            return true;
        }
    };
    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
}
```

```
Add my response with cookie :

    public static void main(String[] args) {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("username", testUser);
            params.add("password", testPass);
NullHostnameVerifier verifier = new NullHostnameVerifier(); 
            MySimpleClientHttpRequestFactory requestFactory = new MySimpleClientHttpRequestFactory(verifier , rememberMeCookie);
            ResponseEntity<String> response = restTemplate.postForEntity(appUrl + "/login", params, String.class);

            HttpHeaders headers = response.getHeaders();
            String cookieResponse = headers.getFirst("Set-Cookie");
            String[] cookieParts = cookieResponse.split(";");
            rememberMeCookie = cookieParts[0];
            cookie.setCookie(rememberMeCookie);

            requestFactory = new  MySimpleClientHttpRequestFactory(verifier,cookie.getCookie());
            restTemplate.setRequestFactory(requestFactory);
    }


    public class MySimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

        private final HostnameVerifier verifier;
        private final String cookie;

        public MySimpleClientHttpRequestFactory(HostnameVerifier verifier ,String cookie) {
            this.verifier = verifier;
            this.cookie = cookie;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
                ((HttpsURLConnection) connection).setSSLSocketFactory(trustSelfSignedSSL().getSocketFactory());
                ((HttpsURLConnection) connection).setAllowUserInteraction(true);
                String rememberMeCookie = cookie == null ? "" : cookie; 
                ((HttpsURLConnection) connection).setRequestProperty("Cookie", rememberMeCookie);
            }
            super.prepareConnection(connection, httpMethod);
        }

        public SSLContext trustSelfSignedSSL() {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                ctx.init(null, new TrustManager[] { tm }, null);
                SSLContext.setDefault(ctx);
                return ctx;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }


    public class NullHostnameVerifier implements HostnameVerifier {
           public boolean verify(String hostname, SSLSession session) {
              return true;
           }
        }
```

```
public RestTemplate getRestTemplateBypassingHostNameVerifcation() {
    CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    return new RestTemplate(requestFactory);
}
```










