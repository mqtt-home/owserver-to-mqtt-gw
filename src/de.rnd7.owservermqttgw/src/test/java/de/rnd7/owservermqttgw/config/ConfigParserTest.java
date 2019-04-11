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
			assertEquals("http://localhost:2121", config.getServer());
			assertEquals(2, config.getSensors().size());
		}
	}
}
