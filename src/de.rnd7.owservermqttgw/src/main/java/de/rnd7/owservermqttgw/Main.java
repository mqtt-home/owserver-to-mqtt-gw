package de.rnd7.owservermqttgw;

import java.io.File;

import de.rnd7.owservermqttgw.owserver.OWServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rnd7.owservermqttgw.config.Config;
import de.rnd7.owservermqttgw.config.ConfigParser;
import de.rnd7.owservermqttgw.mqtt.GwMqttClient;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private final Config config;

	public Main(final Config config) {
		LOGGER.debug("Debug enabled");
		LOGGER.info("Info enabled");
		this.config = config;
		Events.register(new GwMqttClient(config));

		try {
			new OWServerService(config.getOwServer(), config.getMqtt().isDeduplicate())
					.start(config.getMqtt().getPollingInterval());

		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			LOGGER.error("Expected configuration file as argument");
			return;
		}
		
		new Main(ConfigParser.parse(new File(args[0])));
	}

}
