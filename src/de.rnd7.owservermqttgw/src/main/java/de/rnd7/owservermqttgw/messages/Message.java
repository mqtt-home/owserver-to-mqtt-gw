package de.rnd7.owservermqttgw.messages;

public abstract class Message {
	
	private String topic;

	public Message(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}

	public abstract String getValueString();

}
