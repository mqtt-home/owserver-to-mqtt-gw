package de.rnd7.owservermqttgw.owserver.sensor;

import de.rnd7.mqttgateway.Events;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemperatureSensor extends Sensor {
    public TemperatureSensor(final String uuid, final String topic) {
        super(uuid, topic);
    }

    private boolean isValidTemperature(final Double temperature) {
        return temperature != null && temperature > -199;
    }

    public void exec(final Map<String, String> data) throws IOException, NumberFormatException {
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
            // TODO: errorMessage    
        }
    }
}
