package de.rnd7.owservermqttgw;

import java.io.File;

import de.rnd7.mqttgateway.GwMqttClient;
import de.rnd7.mqttgateway.config.ConfigParser;
import de.rnd7.owservermqttgw.owserver.OWServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rnd7.owservermqttgw.config.Config;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public Main(final Config config) {
        LOGGER.debug("Debug enabled");
        LOGGER.info("Info enabled");


        try {
            final GwMqttClient client = GwMqttClient.start(config.getMqtt()
                .setDefaultTopic("owserver"));

            client.online();

            new OWServerService(config.getOwServer())
                .start(config.getOwServer().getPollingInterval());

        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void main(final String[] args) throws Exception {
        if (args.length != 1) {
            LOGGER.error("Expected configuration file as argument");
            return;
        }

        new Main(ConfigParser.parse(new File(args[0]), Config.class));
    }

}
