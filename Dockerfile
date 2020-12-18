# ---- Build ----
FROM openjdk:8-jdk-alpine as builder

LABEL maintainer="Philipp Arndt <2f.mail@gmx.de>"
LABEL version="1.0"
LABEL description="owserver to mqtt gateway"


ENV LANG en_US.UTF-8
ENV TERM xterm

WORKDIR /opt/owserver-to-mqtt-gw

RUN apk update --no-cache && apk add --no-cache maven

COPY src /opt/owserver-to-mqtt-gw

RUN mvn install assembly:single

# ---- Prod ----
FROM openjdk:8-jdk-alpine
RUN mkdir /opt/app
WORKDIR /opt/app
COPY --from=builder /opt/owserver-to-mqtt-gw/de.rnd7.owservermqttgw/target/owserver-to-mqtt-gw.jar ./owserver-to-mqtt-gw.jar
COPY logback.xml .

ENV LOGBACK_XML ./owserver-to-mqtt-gw.jar/logback.xml
CMD java -Dlogback.configurationFile=$LOGBACK_XML -jar ./owserver-to-mqtt-gw.jar /var/lib/owserver-to-mqtt-gw/config.json
