FROM openjdk:11 as backend
RUN apt-get update && apt-get install maven -y
WORKDIR /src
COPY / .
RUN mvn install
ENTRYPOINT exec java -cp target/demo-0.0.1.jar -Dloader.path=lib/ org.springframework.boot.loader.PropertiesLauncher