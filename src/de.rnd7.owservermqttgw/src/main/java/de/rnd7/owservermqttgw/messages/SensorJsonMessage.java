package de.rnd7.owservermqttgw.messages;

import org.json.JSONObject;

public class SensorJsonMessage extends Message {
	
	private Double temperature;
	private Double humidity;

	public SensorJsonMessage(String topic, Double temperature, Double humidity) {
		super(topic);
		this.humidity = humidity;
		this.temperature = temperature;
	}

	public String getValueString() {
		JSONObject jsonObject = new JSONObject();

		if (temperature != null) {
			jsonObject.put("temperature", temperature);
		}

		if (humidity != null) {
			jsonObject.put("humidity", humidity);
		}

		return jsonObject.toString();
	}

}
