package de.rnd7.owservermqttgw;

public class Sensor {
	private String uuid;
	private String name;

	public Sensor(String uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
}
