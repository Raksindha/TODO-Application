FROM openjdk:17
LABEL maintainer="niit.com"
WORKDIR usr/lib
ADD target/UserAuthentication-0.0.1-SNAPSHOT.jar /usr/lib/app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
