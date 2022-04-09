FROM openjdk:11
WORKDIR '/app'
ADD target/demo-0.0.1-SNAPSHOT.jar .
EXPOSE 9094
COPY . .
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]