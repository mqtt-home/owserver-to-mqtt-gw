package de.rnd7.owservermqttgw.mqtt;

import java.util.Optional;

import de.rnd7.owservermqttgw.config.ConfigMqtt;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;

import de.rnd7.owservermqttgw.config.Config;
import de.rnd7.owservermqttgw.messages.Message;

public class GwMqttClient {
	private static final int QOS = 2;

	private static final Logger LOGGER = LoggerFactory.getLogger(GwMqttClient.class);

	private final MemoryPersistence persistence = new MemoryPersistence();
	private final Object mutex = new Object();
	private final Config config;

	private Optional<MqttClient> client = Optional.empty();
	
	public GwMqttClient(final Config config) {
		this.config = config;
		this.client = this.connect();
	}
	
	private Optional<MqttClient> connect() {
		try {
			ConfigMqtt mqtt = this.config.getMqtt();

			LOGGER.info("Connecting MQTT client");
			final MqttClient result = new MqttClient(mqtt.getUrl(), mqtt.getClientId(), this.persistence);
			result.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable cause) {
					LOGGER.error(cause.getMessage(), cause);
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					// do nothing
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// do nothing
				}
			});

			final MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			mqtt.getUsername().ifPresent(connOpts::setUserName);
			mqtt.getPassword().map(String::toCharArray).ifPresent(connOpts::setPassword);

			result.connect(connOpts);
			LOGGER.info("Connecting MQTT client DONE");

			return Optional.of(result).filter(MqttClient::isConnected);
		} catch (final MqttException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage(), e);
			}
			else {
				LOGGER.error(e.getMessage());
			}
			return Optional.empty();
		}
	}

	private void publish(final String topic, final String value) {
		synchronized(this.mutex) {
			if (!this.client.filter(MqttClient::isConnected).isPresent()) {
				this.client = this.connect();
			}
			
			this.client.ifPresent(mqttClient -> {
				try {
					final MqttMessage message = new MqttMessage(value.getBytes());
					message.setQos(this.config.getMqtt().getQos());
					message.setRetained(this.config.getMqtt().isRetain());
					mqttClient.publish(topic, message);
				} catch (final MqttException e) {
					LOGGER.error(e.getMessage(), e);
				}
			});
		}
	}

	@Subscribe
	public void publish(final Message message) {
		final String topic = message.getTopic();
		final String valueString = message.getValueString();
		
		LOGGER.debug("{} = {}", topic, valueString);
		
		this.publish(topic, valueString);
	}
	
}
