package de.rnd7.owservermqttgw.messages;

import de.rnd7.mqttgateway.PublishMessage;
import org.json.JSONObject;

public class SensorMessageFactory {

    private SensorMessageFactory() {

    }

    public static PublishMessage create(final String topic, final Double temperature, final Double humidity) {
        final JSONObject jsonObject = new JSONObject();

        if (temperature != null) {
            jsonObject.put("temperature", temperature);
        }

        if (humidity != null) {
            jsonObject.put("humidity", humidity);
        }

        return PublishMessage.absolute(topic, jsonObject.toString());
    }

}
