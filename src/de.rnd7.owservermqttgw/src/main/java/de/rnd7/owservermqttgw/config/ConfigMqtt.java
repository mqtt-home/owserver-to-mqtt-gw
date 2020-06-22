package de.rnd7.owservermqttgw.config;
import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.util.Optional;

public class ConfigMqtt {
    private String url;
    private String username;
    private String password;
    private boolean retain = false;

    @SerializedName("message-interval")
    private Duration pollingInterval = Duration.ofSeconds(60);

    @SerializedName("client-id")
    private String clientId = "owserver-mqtt-gw";

    @SerializedName("qos")
    private int qos = 2;

    public String getUrl() {
        return url;
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }

    public boolean isRetain() {
        return retain;
    }

    public int getQos() {
        return qos;
    }

    public String getClientId() {
        return clientId;
    }

}
