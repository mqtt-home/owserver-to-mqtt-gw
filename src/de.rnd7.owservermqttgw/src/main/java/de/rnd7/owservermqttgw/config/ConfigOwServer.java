package de.rnd7.owservermqttgw.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigOwServer {
    private String url;
    private List<ConfigSensor> sensors = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public List<ConfigSensor> getSensors() {
        return sensors;
    }
}
