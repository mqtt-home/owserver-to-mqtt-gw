package de.rnd7.owservermqttgw.owserver.sensor;

import de.rnd7.mqttgateway.Events;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TemperatureHumiditySensor extends Sensor {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureHumiditySensor.class);

    public TemperatureHumiditySensor(final String uuid, final String topic) {
        super(uuid, topic);
    }

    private boolean isValidTemperature(final Double temperature) {
        return temperature != null && temperature > -199;
    }

    private boolean isValidHumidity(final Double humidity) {
        return humidity != null && humidity >= 0.0 && humidity <= 100.0;
    }

    public void exec(final Map<String, String> data) {
        final Map<String, Object> out = new HashMap<>();

        final String temperatureRaw = data.get("temperature");
        final String humidityRaw = data.get("humidity");

        if (temperatureRaw != null) {
            if (humidityRaw != null) {
                final Double humidity = Double.valueOf(humidityRaw); 
                if (isValidHumidity(humidity)) {
                    out.put("humidity", humidity);
                }
            }
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
