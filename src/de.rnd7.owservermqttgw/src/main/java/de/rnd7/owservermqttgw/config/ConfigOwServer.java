package de.rnd7.owservermqttgw.config;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ConfigOwServer {
    private String url;
    private List<ConfigSensor> sensors = new ArrayList<>();

    @SerializedName("polling-interval")
    private Duration pollingInterval = Duration.ofSeconds(60);

    public String getUrl() {
        return url;
    }

    public List<ConfigSensor> getSensors() {
        return sensors;
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }
}
