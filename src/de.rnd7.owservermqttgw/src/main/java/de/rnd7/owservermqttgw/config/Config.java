package de.rnd7.owservermqttgw.config;

public class Config {

    private ConfigMqtt mqtt = new ConfigMqtt();
    private ConfigOwServer owserver = new ConfigOwServer();

    public ConfigMqtt getMqtt() {
        return mqtt;
    }

    public ConfigOwServer getOwServer() {
        return owserver;
    }

}
