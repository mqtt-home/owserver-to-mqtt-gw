# owserver-to-mqtt-gw

This project converts temperature sensor messages from the OWServer to mqtt.

## Docker Compose Examples

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

### owservermqtt
```
owservermqtt:
  hostname: owservermqtt
  image: pharndt/owservermqtt:1.0.2
  volumes:
   - ./config/heizung:/var/lib/owserver-to-mqtt-gw:ro
  restart: always
  depends_on:
   - owfs
   - mosquitto
```


## Installation

Checkout `production` and place a configuration in the config folder.

Run `docker-compose up` to start

## Configuration
See [example configuration](config-example.json) for full example.

```json
{
  "mqtt": {
    "url": "tcp://192.168.2.2:1883",

    "username": "myuser",
    "password": "mypassword",

    "message-interval": 60,
  },

  "owserver": {
    "url": "http://192.168.2.1:2121",

    "sensors": [
      { "uid": "28.AABBCCDDEE01", "topic": "home/temperature/sensor1"	},
      { "uid": "28.AABBCCDDEE02", "topic": "home/temperature/sensor2" },
      { "uid": "26.111111111111", "topic": "home/humidity/sensor1" }
    ]
  }
}
```

# mqtt configuration

| parameter        | description                     | default            |
| ---------------- | ------------------------------- | ------------------ |
| url              | MQTT Server                     |                    |
| username         | username for user/password auth | no authentication  |
| password         | password for user/password auth | no authentication  |
| message-interval | in secounds                     | `60`               |
| client-id        |                                 | `owserver-mqtt-gw` |
| retain           |                                 | `false`            |
| qos              | 0, 1, 2                         | `2`                |
|                  |                                 |                    |

# owserver configuration

| parameter | description             |
| --------- | ----------------------- |
| url       | OWServer address        |
| sensors   | Array of sensor objects |
