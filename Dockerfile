# ---- Prod ----
FROM openjdk:8-jdk-alpine
LABEL maintainer="Philipp Arndt <2f.mail@gmx.de>"
LABEL version="1.0"
LABEL description="owserver to mqtt gateway"

RUN mkdir /opt/app
WORKDIR /opt/app
COPY src/de.rnd7.owservermqttgw/target/owserver-to-mqtt-gw.jar .
COPY logback.xml .

CMD java -jar ./owserver-to-mqtt-gw.jar /var/lib/owserver-to-mqtt-gw/config.json
