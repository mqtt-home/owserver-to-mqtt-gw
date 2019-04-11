package de.rnd7.owservermqttgw.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import de.rnd7.owservermqttgw.Sensor;

public class Config {
	private String server;
	private String mqttBroker;
	
	private List<Sensor> sensors = new ArrayList<>();
	private Duration pollingInterval;
	private String fullMessageTopic; 
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setMqttBroker(final String mqttBroker) {
		this.mqttBroker = mqttBroker;
	}
	
	public String getMqttBroker() {
		return this.mqttBroker;
	}
	
	public void addSensor(Sensor sensor) {
		sensors.add(sensor);
	}
	
	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setPollingInterval(Duration pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
	
	public Duration getPollingInterval() {
		return pollingInterval;
	}

	public void setFullMessageTopic(String fullMessageTopic) {
		this.fullMessageTopic = fullMessageTopic;
	}
	
	public String getFullMessageTopic() {
		return this.fullMessageTopic;
	}
	
}
