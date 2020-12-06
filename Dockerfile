FROM openjdk:11-jdk

RUN mkdir /var/circleci-with-springboot

ARG DEPENDENCY=build
ADD ${DEPENDENCY}/libs/springbootjpa-0.0.1-SNAPSHOT.jar /var/circleci-with-springboot/springbootjpa-0.0.1.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/var/circleci-with-springboot/springbootjpa-0.0.1.jar"]
