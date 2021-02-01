package de.rnd7.owservermqttgw.owserver.sensor;

import de.rnd7.mqttgateway.Events;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemperatureHumiditySensor extends Sensor {
    public TemperatureHumiditySensor(final String uuid, final String topic) {
        super(uuid, topic);
    }

    private boolean isValidTemperature(final Double temperature) {
        return temperature != null && temperature > -199;
    }

    private boolean isValidHumidity(final Double humidity) {
        return humidity != null && humidity >= 0.0 && humidity <= 100.0;
    }

    public void exec(final Map<String, String> data) throws IOException, NumberFormatException {
        final Map<String, Object> out = new HashMap<>();

        final Double temperature = Double.valueOf(data.get("temperature"));
        final Double humidity = Double.valueOf(data.get("humidity"));

        if (isValidTemperature(temperature)) {
            out.put("temperature", temperature);
        }
        if (isValidHumidity(humidity)) {
            out.put("humidity", humidity);
        }

        Events.post(SensorMessageFactory.create(getTopic(), out));
    }
}
