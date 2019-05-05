package de.rnd7.owservermqttgw.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import de.rnd7.owservermqttgw.Sensor;

public class ConfigParser {
	private static final String FULL_MESSAGE_TOPIC = "full-message-topic";
	private static final String MESSAGE_INTERVAL = "message-interval";
	private static final String MESSAGE_TYPE = "message-type";

	private ConfigParser() {
		
	}
	
	public static Config parse(final File file) throws IOException {
		try (InputStream in = new FileInputStream(file)) {
			return parse(in);
		}
	}
	
	public static Config parse(final InputStream in) throws IOException {
		final Config config = new Config();
		
		final JSONObject jsonObject = new JSONObject(IOUtils.toString(in, StandardCharsets.UTF_8));
		config.setServer(jsonObject.getString("server"));
		config.setMqttBroker(jsonObject.getString("mqtt-url"));
		config.setPollingInterval(Duration.ofSeconds(jsonObject.getInt(MESSAGE_INTERVAL)));
		
		if (jsonObject.has(FULL_MESSAGE_TOPIC)) {
			config.setFullMessageTopic(jsonObject.getString(FULL_MESSAGE_TOPIC));
		}
		
		config.setJsonMessages(jsonObject.has(MESSAGE_TYPE) && jsonObject.getString(MESSAGE_TYPE).toLowerCase().equals("json"));
		
		
		final JSONObject mapping = (JSONObject) jsonObject.get("sensors");
		
		for (final String uid : JSONObject.getNames(mapping)) {
			config.addSensor(new Sensor(uid, mapping.getString(uid)));	
		}
		
		return config;

	}
}
