DELETE    delete(java.lang.String, java.lang.Object...)

GET       getForObject(java.lang.String, java.lang.Class, java.lang.Object...)
          getForEntity(java.lang.String, java.lang.Class, java.lang.Object...)

HEAD      headForHeaders(java.lang.String, java.lang.Object...)

OPTIONS   optionsForAllow(java.lang.String, java.lang.Object...)

POST      postForLocation(java.lang.String, java.lang.Object, java.lang.Object...)
          postForObject(java.lang.String, java.lang.Object, java.lang.Class, java.lang.Object...)

PUT       put(java.lang.String, java.lang.Object, java.lang.Object...)

any       exchange(java.lang.String, org.springframework.http.HttpMethod, org.springframework.http.HttpEntity, java.lang.Class, java.lang.Object...)
          execute(java.lang.String, org.springframework.http.HttpMethod, org.springframework.web.client.RequestCallback, org.springframework.web.client.ResponseExtractor, java.lang.Object...)
