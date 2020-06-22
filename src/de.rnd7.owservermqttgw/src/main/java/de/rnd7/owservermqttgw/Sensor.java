package de.rnd7.owservermqttgw;

public class Sensor {
	private String uuid;
	private String topic;

	public Sensor(String uuid, String topic) {
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
