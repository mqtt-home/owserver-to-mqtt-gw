package de.rnd7.owservermqttgw.owserver.sensor;

import de.rnd7.mqttgateway.Events;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TemperatureSensor extends Sensor {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureSensor.class);

    public TemperatureSensor(final String uuid, final String topic) {
        super(uuid, topic);
    }

    private boolean isValidTemperature(final Double temperature) {
        return temperature != null && temperature > -199;
    }

    public void exec(final Map<String, String> data) {
        final Map<String, Object> out = new HashMap<>();

        final String temperatureRaw = data.get("temperature");

        if (temperatureRaw != null) {
            final Double temperature = Double.valueOf(temperatureRaw); 
            if (isValidTemperature(temperature)) {
                out.put("temperature", temperature);
            }
            Events.post(SensorMessageFactory.create(getTopic(), out));
        }
        else {
            logger.error("TemperatureHumiditySensor data map does not contain temperature value");
        }
    }
}
