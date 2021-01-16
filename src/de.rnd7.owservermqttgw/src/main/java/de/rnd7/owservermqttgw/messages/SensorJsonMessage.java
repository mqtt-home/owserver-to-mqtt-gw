package de.rnd7.owservermqttgw.messages;

import org.json.JSONObject;

public class SensorJsonMessage extends Message {

    private final Double temperature;
    private final Double humidity;

    public SensorJsonMessage(final String topic, final Double temperature, final Double humidity) {
        super(topic);
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public String getValueString() {
        final JSONObject jsonObject = new JSONObject();

        if (temperature != null) {
            jsonObject.put("temperature", temperature);
        }

        if (humidity != null) {
            jsonObject.put("humidity", humidity);
        }

        return jsonObject.toString();
    }

}
