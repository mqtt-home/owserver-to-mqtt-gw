package de.rnd7.owservermqttgw.messages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class FullMessage extends Message {

    private List<SensorMessage> messages = new ArrayList<>();

    public FullMessage(final String topic) {
        super(topic);
    }

    @Override
    public String getValueString() {
        final JSONObject message = new JSONObject();

        for (SensorMessage sensorMessage : messages) {
            message.put(sensorMessage.getTopic().replace("/", "."), sensorMessage.getValueDouble());
        }

        return message.toString();
    }

    public void add(final SensorMessage message) {
        this.messages.add(message);
    }
}
