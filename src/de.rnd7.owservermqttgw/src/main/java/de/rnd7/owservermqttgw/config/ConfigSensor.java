package de.rnd7.owservermqttgw.config;

import java.util.Optional;

public class ConfigSensor {
    private String uid;
    private String topic;
    private String type;
    private String key;

    public String getUid() {
        return uid;
    }

    public String getTopic() {
        return topic;
    }

    public String getType() {
        // due to compatibility of config files, default is set to TemperatureHumidity
        final String result = Optional.ofNullable(type).orElse("TemperatureHumidity");
        
        return result;
    }

    public String getKey() {
        return key;
    }
}
