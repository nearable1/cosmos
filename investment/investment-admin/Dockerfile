FROM java:8
EXPOSE 8081

VOLUME /tmp
ADD autoapproval-admin.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
