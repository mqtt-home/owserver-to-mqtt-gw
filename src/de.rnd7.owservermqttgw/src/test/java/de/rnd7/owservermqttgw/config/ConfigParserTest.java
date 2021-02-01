package de.rnd7.owservermqttgw.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.rnd7.mqttgateway.config.ConfigMqtt;
import de.rnd7.mqttgateway.config.ConfigParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ConfigParserTest {

    @Test
    void test_load_config() throws IOException {
        try (final InputStream in = ConfigParserTest.class.getResourceAsStream("config-example.json")) {
            final Config config = ConfigParser.parse(in, Config.class);
            final ConfigMqtt mqtt = config.getMqtt();
            assertEquals("tcp://localhost:1883", mqtt.getUrl());
            assertTrue(mqtt.isRetain());
            assertEquals(1, mqtt.getQos());
            assertEquals("client-id", mqtt.getClientId().get());
            assertEquals("MaxMustermann", mqtt.getUsername().orElse(null));
            assertEquals("Geheim!", mqtt.getPassword().orElse(null));

            final ConfigOwServer owServer = config.getOwServer();
            assertEquals("http://localhost:2121", owServer.getUrl());
            final List<ConfigSensor> sensors = owServer.getSensors();
            assertEquals(3, sensors.size());
            assertEquals(SensorType.temperature_humidity, sensors.get(0).getType());
            assertEquals(SensorType.temperature, sensors.get(1).getType());
            assertEquals(SensorType.counter, sensors.get(2).getType());
            assertEquals("counter.A", sensors.get(2).getKey());
        }
    }

    @Test
    void test_load_config_minimal() throws IOException {
        try (final InputStream in = ConfigParserTest.class.getResourceAsStream("config-example-minimal.json")) {
            final Config config = ConfigParser.parse(in, Config.class);
            final ConfigMqtt mqtt = config.getMqtt();

            assertEquals("tcp://localhost:1883", mqtt.getUrl());
            assertTrue(mqtt.isRetain());
            assertEquals(2, mqtt.getQos());
            assertEquals("http://localhost:2121", config.getOwServer().getUrl());
            assertFalse(mqtt.getUsername().isPresent());
            assertFalse(mqtt.getPassword().isPresent());
            assertEquals(2, config.getOwServer().getSensors().size());
        }
    }

    @Test
    void test_empty_credentials() {
        final Config config = new Config();
        final ConfigMqtt mqtt = config.getMqtt();
        assertFalse(mqtt.getUsername().isPresent());
        assertFalse(mqtt.getPassword().isPresent());
    }

}
