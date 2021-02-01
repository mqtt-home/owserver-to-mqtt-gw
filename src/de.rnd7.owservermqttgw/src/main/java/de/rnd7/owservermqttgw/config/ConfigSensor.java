package de.rnd7.owservermqttgw.config;

public class ConfigSensor {
    private String uid;
    private String topic;
    private SensorType type = SensorType.temperature_humidity;
    private String key;

    public String getUid() {
        return uid;
    }

    public String getTopic() {
        return topic;
    }

    public SensorType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }
}
