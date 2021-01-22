# owserver-to-mqtt-gw

[![mqtt-smarthome](https://img.shields.io/badge/mqtt-smarthome-blue.svg)](https://github.com/mqtt-smarthome/mqtt-smarthome)

This project converts temperature and humidity sensor messages from the OWServer to MQTT.

This project comes with two docker images, the main project `pharndt/owservermqtt` and an optional image `pharndt/ds9490r-owfs` containing OWServer and the driver for a DS9490R USB stick.

## Installation

I recommend using the docker images (see docker examples).

## Configuration

See [example configuration](config-example.json) for full example.

```json
{
  "mqtt": {
    "url": "tcp://192.168.2.2:1883",

    "username": "myuser",
    "password": "mypassword",

    "deduplicate": true
  },

  "owserver": {
    "url": "http://192.168.2.1:2121",
    "polling-interval": 60,
      
    "sensors": [
      { "uid": "28.AABBCCDDEE01", "topic": "home/temperature/sensor1"  },
      { "uid": "28.AABBCCDDEE02", "topic": "home/temperature/sensor2" },
      { "uid": "26.111111111111", "topic": "home/humidity/sensor1" }
    ]
  }
}
```

# MQTT configuration

| parameter        | description                     | default            |
| ---------------- | ------------------------------- | ------------------ |
| url              | MQTT Server                     |                    |
| username         | username for user/password auth | no authentication  |
| password         | password for user/password auth | no authentication  |
| client-id        |                                 | `owserver-mqtt-gw` |
| retain           |                                 | `false`            |
| qos              | 0, 1, 2                         | `2`                |
| deduplicate      | Deduplicate messages            | `false`            |
# OWServer configuration

| parameter        | description             | default |
| ---------------- | ----------------------- | ------- |
| url              | OWServer address        |         |
| polling-interval | in seconds              | `60`    |
| sensors          | Array of sensor objects |         |

## Docker Compose Examples

### owservermqtt

```
owservermqtt:
  hostname: owservermqtt
  image: pharndt/owservermqtt:1.0.4
  volumes:
   - ./config/heizung:/var/lib/owserver-to-mqtt-gw:ro
  restart: always
  depends_on:
   - owfs
   - mosquitto
```

### pharndt/ds9490r-owfs

```
owfs:
  privileged: true # usb access
  hostname: owfs
  image: pharndt/ds9490r-owfs
  expose:
      - "2121"
  ports:
      - "2121:2121"
  volumes:
    - /dev/bus/usb:/dev/bus/usb
  restart: always
```
