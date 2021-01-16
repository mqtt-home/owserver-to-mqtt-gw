package de.rnd7.owservermqttgw.messages;

import org.json.JSONObject;

public class SensorMessage extends Message {

    private final String message;

    public SensorMessage(final String topic, final Double temperature, final Double humidity) {
        super(topic);
        this.message = toMessage(temperature, humidity);
    }

    private String toMessage(final Double temperature, final Double humidity) {
        final JSONObject jsonObject = new JSONObject();

        if (temperature != null) {
            jsonObject.put("temperature", temperature);
        }

        if (humidity != null) {
            jsonObject.put("humidity", humidity);
        }

        return jsonObject.toString();
    }

    @Override
    public String getValueString() {
        return message;
    }

}
