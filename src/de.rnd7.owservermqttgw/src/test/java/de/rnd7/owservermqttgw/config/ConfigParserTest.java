package de.rnd7.owservermqttgw.config;

import java.io.InputStream;

import de.rnd7.mqttgateway.config.ConfigParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConfigParserTest {

    @Test
    public void test_load_config() throws Exception {
        try (final InputStream in = ConfigParserTest.class.getResourceAsStream("config-example.json")) {
            final Config config = ConfigParser.parse(in, Config.class);
            assertEquals("tcp://localhost:1883", config.getMqtt().getUrl());
            assertTrue(config.getMqtt().isRetain());
            assertEquals(1, config.getMqtt().getQos());
            assertEquals("client-id", config.getMqtt().getClientId().get());
            assertEquals("http://localhost:2121", config.getOwServer().getUrl());
            assertEquals("MaxMustermann", config.getMqtt().getUsername().orElse(null));
            assertEquals("Geheim!", config.getMqtt().getPassword().orElse(null));
            assertEquals(2, config.getOwServer().getSensors().size());
        }
    }

    @Test
    public void test_load_config_minimal() throws Exception {
        try (final InputStream in = ConfigParserTest.class.getResourceAsStream("config-example-minimal.json")) {
            final Config config = ConfigParser.parse(in, Config.class);
            assertEquals("tcp://localhost:1883", config.getMqtt().getUrl());
            assertTrue(config.getMqtt().isRetain());
            assertEquals(2, config.getMqtt().getQos());
            assertEquals("http://localhost:2121", config.getOwServer().getUrl());
            assertFalse(config.getMqtt().getUsername().isPresent());
            assertFalse(config.getMqtt().getPassword().isPresent());
            assertEquals(2, config.getOwServer().getSensors().size());
        }
    }

    @Test
    public void test_empty_credentials() throws Exception {
        Config config = new Config();
        assertFalse(config.getMqtt().getUsername().isPresent());
        assertFalse(config.getMqtt().getPassword().isPresent());
    }

}
