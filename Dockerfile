FROM java:8
LABEL maintainer="Thomaskoh89@hotmail.com"
EXPOSE 8080
WORKDIR /app
COPY target/wallet-0.0.1-SNAPSHOT.jar /app/wallet.jar
ENTRYPOINT ["java","-jar","wallet.jar"]