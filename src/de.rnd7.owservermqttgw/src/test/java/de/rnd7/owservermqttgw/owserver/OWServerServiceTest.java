package de.rnd7.owservermqttgw.owserver;

import de.rnd7.owservermqttgw.config.ConfigOwServer;
import de.rnd7.owservermqttgw.messages.SensorMessage;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class OWServerServiceTest extends TestCase {
    @Test
    public void test_no_deduplication() {
    	final OWServerService service = new OWServerService(new ConfigOwServer(), false);

        final SensorMessage message = new SensorMessage("topic", 24d, 50d);

        Assert.assertTrue(service.handleDeduplication("sensor-1", message));
        Assert.assertTrue(service.handleDeduplication("sensor-1", message));
    }

    @Test
    public void test_deduplication() {
        final OWServerService service = new OWServerService(new ConfigOwServer(), true);

        final SensorMessage message = new SensorMessage("topic", 24d, 50d);

        Assert.assertTrue(service.handleDeduplication("sensor-1", message));
        Assert.assertFalse(service.handleDeduplication("sensor-1", message));
    }

    @Test
    public void test_deduplication_then_changed() {
        final OWServerService service = new OWServerService(new ConfigOwServer(), true);

        Assert.assertTrue(service.handleDeduplication("sensor-1", new SensorMessage("topic", 24d, 50d)));
        Assert.assertTrue(service.handleDeduplication("sensor-1", new SensorMessage("topic", 24d, 50.1d)));
    }

    @Test
    public void test_deduplication_other_sensor_same_value() {
        final OWServerService service = new OWServerService(new ConfigOwServer(), true);

        final SensorMessage message = new SensorMessage("topic", 24d, 50d);

        Assert.assertTrue(service.handleDeduplication("sensor-1", message));
        Assert.assertTrue(service.handleDeduplication("sensor-2", message));
    }
}
