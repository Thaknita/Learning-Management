FROM openjdk:17-alpine
RUN mkdir -p myapp
COPY ./build/libs/*.jar /myapp/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","/myapp/app.jar"]

