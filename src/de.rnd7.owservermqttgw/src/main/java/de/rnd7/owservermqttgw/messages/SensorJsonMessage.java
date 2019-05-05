package de.rnd7.owservermqttgw.messages;

import org.json.JSONObject;

public class SensorJsonMessage extends Message {
	
	private double value;

	public SensorJsonMessage(String topic, double value) {
		super(topic);
		this.value = value;
	}

	public String getValueString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("temperature", value);
		
		return jsonObject.toString();
	}

	public double getValueDouble() {
		return value;
	}

}
