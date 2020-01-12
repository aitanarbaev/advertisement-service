From openjdk:8
copy ./target/advertisement-service-0.0.1-SNAPSHOT.jar /advertisement-service.jar
CMD ["java","-jar","advertisement-service.jar"]

