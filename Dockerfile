FROM eclipse-temurin:17.0.13_11-jdk-alpine@sha256:4708e1a2c3baa0855eb9b3e6ae6285c8640d574c25ba74fddf6b8a17ccc3673f
WORKDIR /usr/app
COPY target/customer-manage*.jar app.jar
RUN addgroup -S app && \
    adduser -S -D -h /usr/app/jar app app && \
	chown -R app:app /usr/app
USER app	
EXPOSE 9093 9092
ENTRYPOINT [ "java", "-jar", "app.jar" ]