FROM java:8
ENV
VOLUME /tmp
EXPOSE 8080
ADD backend-0.0.1.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
