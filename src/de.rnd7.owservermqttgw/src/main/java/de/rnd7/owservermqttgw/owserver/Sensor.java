package de.rnd7.owservermqttgw.owserver;

public class Sensor {
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

}
