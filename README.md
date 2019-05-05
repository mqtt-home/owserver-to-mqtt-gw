# owserver-to-mqtt-gw

This project converts temperature sensor messages from the OWServer to mqtt.

## Installation

Checkout `production` and place a configuration in the config folder.

Run `docker-compose up` to start

## Configuration
See [example configuration](config-example.json)

```
{
	"server": "http://192.168.2.1:2121",
	"mqtt-url": "tcp://192.168.2.2:1883",
	
	"message-interval": 60,
	"full-message-topic": "sensordata",
	"message-type": "json",
	
	"sensors": {
		"28.AABBCCDDEE01": "home/sensor1",
		"28.AABBCCDDEE02": "home/sensor2"
	}
}
````

| parameter          | description                                    |
| server             | OWServer address                               |
| mqtt-url           | MQTT Server                                    |
| message-interval   | in secounds                                    |
| full-message-topic | (optional) send a full message for all sensors |
| message-type       | (optional) json or plain                       |
| sensors            | Array of sensors                               |