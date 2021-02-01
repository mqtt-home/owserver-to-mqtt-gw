package de.rnd7.owservermqttgw.owserver.sensor;

import java.io.IOException;
import java.util.Map;

public abstract class Sensor {
    private String uuid;
    private String topic;

    public Sensor(final String uuid, final String topic) {
        this.uuid = uuid;
        this.topic = topic;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTopic() {
        return topic;
    }

    public abstract void exec(final Map<String, String> data) throws IOException;
}
