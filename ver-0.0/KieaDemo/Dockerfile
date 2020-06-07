FROM openjdk:8-jre
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
COPY target/KieaDemo*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]



