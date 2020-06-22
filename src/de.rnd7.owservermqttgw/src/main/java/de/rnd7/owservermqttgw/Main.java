package de.rnd7.owservermqttgw;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.rnd7.owservermqttgw.config.MessageType;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

import de.rnd7.owservermqttgw.config.Config;
import de.rnd7.owservermqttgw.config.ConfigParser;
import de.rnd7.owservermqttgw.messages.Message;
import de.rnd7.owservermqttgw.messages.SensorJsonMessage;
import de.rnd7.owservermqttgw.mqtt.GwMqttClient;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private static final Pattern PATTERN = Pattern.compile("temperature</b></td><td>(\\d+\\.?\\d*)</td>",
			Pattern.CASE_INSENSITIVE);

	private final EventBus eventBus = new EventBus();

	private final Config config;

	@SuppressWarnings("squid:S2189")
	public Main(final Config config) {
		this.config = config;
		this.eventBus.register(new GwMqttClient(config));
		
		try {
			final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(this::exec, 
					0, 
					config.getMqtt().getPollingInterval().getSeconds(),
					TimeUnit.SECONDS);
			
			while (true) {
				this.sleep();
			}
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void exec() {
		for (Sensor sensor : this.config.getSensors()) {
			try {
				Map<String, Double> data = readSensor(this.config, sensor);
				Double temperature = data.get("temperature");
				Double humidity = data.get("humidity");

				if (temperature != null && temperature > -199) {
					this.eventBus.post(createMessage(sensor, temperature, humidity));
				}
			}
			catch (IOException e) {
				LOGGER.error("Error reading sensor: " + sensor.getUuid() +" " + e.getMessage());
			}
		}
	}

	private Message createMessage(Sensor sensor, Double temperature, Double humidity) {
		return new SensorJsonMessage(sensor.getTopic(), temperature, humidity);
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (final InterruptedException e) {
			LOGGER.debug(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			LOGGER.error("Expected configuration file as argument");
			return;
		}
		
		new Main(ConfigParser.parse(new File(args[0])));
	}

	private static Map<String, Double> readSensor(final Config config, final Sensor sensor) throws IOException {
		try {
			URL url = new URL(String.format("%s/%s", config.getOwServer().getUrl(), sensor.getUuid()));
			try (InputStream in = url.openStream()) {
				return OWServerParser.parse(IOUtils.toString(in, StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private static String parseTemperature(String page) {
		Matcher matcher = PATTERN.matcher(page);
		if (matcher.find()) {
			return matcher.group(1);
		}

		return "-200";
	}

}
