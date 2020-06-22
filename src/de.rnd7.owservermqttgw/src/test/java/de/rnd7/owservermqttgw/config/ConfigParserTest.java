package de.rnd7.owservermqttgw.config;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import de.rnd7.owservermqttgw.config.Config;
import de.rnd7.owservermqttgw.config.ConfigParser;

public class ConfigParserTest {
	@Test
	public void test_load_config() throws Exception {
		try (InputStream in = ConfigParserTest.class.getResourceAsStream("config-example.json")) {
			Config config = ConfigParser.parse(in);
			assertEquals("tcp://localhost:1883", config.getMqtt().getUrl());
			assertTrue(config.getMqtt().isRetain());
			assertEquals(1, config.getMqtt().getQos());
			assertEquals("client-id", config.getMqtt().getClientId());
			assertEquals("http://localhost:2121", config.getOwServer().getUrl());
			assertEquals("MaxMustermann", config.getMqtt().getUsername().orElse(null));
			assertEquals("Geheim!", config.getMqtt().getPassword().orElse(null));
			assertEquals(2, config.getSensors().size());
		}
	}

	@Test
	public void test_load_config_minimal() throws Exception {
		try (InputStream in = ConfigParserTest.class.getResourceAsStream("config-example-minimal.json")) {
			Config config = ConfigParser.parse(in);
			assertEquals("tcp://localhost:1883", config.getMqtt().getUrl());
			assertFalse(config.getMqtt().isRetain());
			assertEquals(2, config.getMqtt().getQos());
			assertEquals("owserver-mqtt-gw", config.getMqtt().getClientId());
			assertEquals("http://localhost:2121", config.getOwServer().getUrl());
			assertFalse(config.getMqtt().getUsername().isPresent());
			assertFalse(config.getMqtt().getPassword().isPresent());
			assertEquals(2, config.getSensors().size());
		}
	}
	
	@Test
	public void test_empty_credentials() throws Exception {
		Config config = new Config();
		assertFalse(config.getMqtt().getUsername().isPresent());
		assertFalse(config.getMqtt().getPassword().isPresent());
	}

}
