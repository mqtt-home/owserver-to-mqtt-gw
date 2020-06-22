package de.rnd7.owservermqttgw.config;

import de.rnd7.owservermqttgw.Sensor;

import java.util.Collection;
import java.util.stream.Collectors;

public class Config {

	private ConfigMqtt mqtt = new ConfigMqtt();
	private ConfigOwServer owserver = new ConfigOwServer();

	public ConfigMqtt getMqtt() {
		return mqtt;
	}

	public ConfigOwServer getOwServer() {
		return owserver;
	}

	public Collection<Sensor> getSensors() {
		return getOwServer().getSensors().stream()
				.map(sensor -> new Sensor(sensor.getUid(), sensor.getTopic()))
				.collect(Collectors.toList());
	}
}
