package de.rnd7.owservermqttgw.owserver.sensor;

import de.rnd7.mqttgateway.Events;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Counter extends Sensor {
    private final String key;

    public Counter(final String uuid, final String topic, final String key) {
        super(uuid, topic);
        this.key = key;
    }

    private boolean isValidValue(final Integer counter) {
        return counter != null && counter >= 0;
    }

    public void exec(final Map<String, String> data) throws IOException, NumberFormatException {
        final Map<String, Object> out = new HashMap<>();

        final Integer value = Integer.valueOf(data.get(this.key));

        if (isValidValue(value)) {
            out.put("counter", value);
        }

        Events.post(SensorMessageFactory.create(getTopic(), out));
    }
}
