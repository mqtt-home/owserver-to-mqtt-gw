package de.rnd7.owservermqttgw.messages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class FullMessage extends Message {

	private List<SensorMessage> messages = new ArrayList<>();
	
	public FullMessage(String topic) {
		super(topic);
	}

	@Override
	public String getValueString() {
		JSONObject message = new JSONObject();
		
		for (SensorMessage sensorMessage : messages) {
			message.put(sensorMessage.getTopic().replace("/", "."), sensorMessage.getValueDouble());
		}
		
		return message.toString();
	}
	
	public void add(SensorMessage message) {
		this.messages.add(message);
	}
}
