# owserver-to-mqtt-gw

This project converts temperature sensor messages from the OWServer to mqtt.

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

| parameter        | description                      | default            |
| ---------------- | -------------------------------- | ------------------ |
| url              | MQTT Server                      |                    |
| username         | Username to log into MQTT Server |                    |
| password         | Password für MQTT Server         |                    |
| message-interval | in secounds                      | `60`               |
| client-id        |                                  | `owserver-mqtt-gw` |
| retain           |                                  | `false`            |
| qos              | 0, 1, 2                          | `2`                |
|                  |                                  |                    |

# owserver configuration

| parameter | description             |
| --------- | ----------------------- |
| url       | OWServer address        |
| sensors   | Array of sensor objects |
