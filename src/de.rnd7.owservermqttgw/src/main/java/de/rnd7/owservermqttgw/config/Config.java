package de.rnd7.owservermqttgw.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.rnd7.owservermqttgw.Sensor;

public class Config {
	private String server;
	private String mqttBroker;

	private Optional<MqttCredentials> mqttCredentials=Optional.empty();
	
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
	
	public Optional<MqttCredentials> getMqttCredentials() {
		return mqttCredentials;
	}
	
	public void setUsername(final String username) {
		initCredentials().setUsername(username);
	}

	private MqttCredentials initCredentials() {
		final MqttCredentials c;
		if (mqttCredentials.isPresent()) {
			c = mqttCredentials.get();
		}
		else {
			c = new MqttCredentials();
			mqttCredentials = Optional.of(c);
		}
		return c;
	}
	
	public void setPassword(final String password) {
		initCredentials().setPassword(password);

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
