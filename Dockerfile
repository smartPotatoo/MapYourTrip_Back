FROM openjdk:21
VOLUME /tmp
COPY build/libs/map_your_trip-backend.jar map_your_trip-backend.jar
ENTRYPOINT ["java","-jar","/map_your_trip-backend.jar","--spring.profiles.active=prod","--DB_HOST=mysql-svc"]