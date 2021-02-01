package de.rnd7.owservermqttgw.owserver.sensor;

import de.rnd7.mqttgateway.Events;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter extends Sensor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Counter.class);
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

        final String valueRaw = data.get(this.key);
        final Integer value = Integer.valueOf(valueRaw);

        if (isValidValue(value)) {
            out.put("counter", value);
        }

        Events.post(SensorMessageFactory.create(getTopic(), out));
    }
}
