package de.rnd7.owservermqttgw.config;
import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.util.Optional;

public class ConfigMqtt {
    private String url;
    private String username;
    private String password;
    private boolean retain = true;
    @SerializedName("message-type")
    private MessageType messageType = MessageType.json;

    @SerializedName("message-interval")
    private Duration pollingInterval;

    @SerializedName("full-message-topic")
    private String fullMessageTopic;

    @SerializedName("client-id")
    private String clientId;

    public String getUrl() {
        return url;
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public String getFullMessageTopic() {
        return fullMessageTopic;
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }

    public boolean isRetain() {
        return retain;
    }

    public Optional<String> getClientId() {
        return Optional.ofNullable(clientId);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public boolean sendFullMessage() {
        return false;
    }
}
