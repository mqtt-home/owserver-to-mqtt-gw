package de.rnd7.owservermqttgw.messages;

import de.rnd7.mqttgateway.PublishMessage;
import java.util.Map;
import org.json.JSONObject;

public class SensorMessageFactory {

    private SensorMessageFactory() {

    }

    public static PublishMessage create(final String topic, final Map<String, Object> map) {
        final JSONObject jsonObject = new JSONObject();

        map.forEach((k, v) -> { if (v != null) jsonObject.put(k, v); } );

        return PublishMessage.absolute(topic, jsonObject.toString());
    }

}
