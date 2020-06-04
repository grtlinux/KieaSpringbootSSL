KieaSpringbootSSL - stmt

---
-. /test/home.html
-. interceptor
-. object from ObjectMapper from 1000-2.json
-. Rest of Stmt: Stmt findBySeqNo(Integer seqNo);
-. https://localhost:8443/
-. test RestTemplate with ssl skip

-. https://localhost:8443/
-. https://localhost:8443/h2
-. https://localhost:8443/stmts
-. https://localhost:8443/stmts/4
-. https://localhost:8443/profile
-. https://localhost:8443/profile/stmts
---

- CLI 접속확인
```
$ curl -k https://localhost:8443/test/home
```

- gradle: build.gradle의 dependencies
```
dependencies {
	// manual
	implementation 'org.apache.httpcomponents:httpclient'

	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-data-rest'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-webflux'
	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
	testImplementation 'io.projectreactor:reactor-test'
}

```

```
spring:

  h2.console:
    enabled: true
    path: /h2
    settings:
      trace: false
      web-allow-others: true
  
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:
  
  jpa:
    show-sql: true
    database: h2
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate.ddl-auto: update
  
  thymeleaf:
    cache: false
    encoding: utf-8
    prefix: classpath:templates/
    suffix: .html
  
  devtools.livereload.enabled: true

  servlet.multipart:
      max-file-size: 5GB
      max-request-size: 5GB

# SSL JOB-1
# server:
#   port: 8443
#   ssl:
#     key-alias: https-example
#     key-store-type: JKS
#     key-password: password
#     key-store: classpath:https-example.jks

# SSL JOB-2
server:
  port: 8443
  ssl:
    enabled: true
    key-store: classpath:serverkeystore.p12
    key-store-password: server
    key-alias: serverkey
    key-store-type: PKCS12

```



