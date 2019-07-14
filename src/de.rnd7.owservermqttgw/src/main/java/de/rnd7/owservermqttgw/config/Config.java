package de.rnd7.owservermqttgw.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import de.rnd7.owservermqttgw.Sensor;

public class Config {
	private String server;
	private String mqttBroker;
	private String mqttUsername = "";
	private String mqttPassword = "";
	
	private List<Sensor> sensors = new ArrayList<>();
	private Duration pollingInterval;
	private String fullMessageTopic;
	
	private boolean jsonMessages = true;
	
	public boolean isJsonMessages() {
		return jsonMessages;
	}
	
	public void setJsonMessages(boolean jsonMessages) {
		this.jsonMessages = jsonMessages;
	}
	
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
	
	public void setUsername(final String username) {
		this.mqttUsername = username;
	}
	
	public String getUsername() {
		return this.mqttUsername;
	}
	
	public void setPassword(final String password) {
		this.mqttPassword = password;
	}
	
	public String getPassword() {
		return this.mqttPassword;
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

	public boolean sendFullMessage() {
		return fullMessageTopic != null;
	}
}
