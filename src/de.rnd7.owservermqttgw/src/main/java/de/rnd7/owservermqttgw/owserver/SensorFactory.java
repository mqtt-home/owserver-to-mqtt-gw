package de.rnd7.owservermqttgw.owserver;

import de.rnd7.owservermqttgw.config.ConfigSensor;
import de.rnd7.owservermqttgw.owserver.sensor.Sensor;
import de.rnd7.owservermqttgw.owserver.sensor.Counter;
import de.rnd7.owservermqttgw.owserver.sensor.TemperatureSensor;
import de.rnd7.owservermqttgw.owserver.sensor.TemperatureHumiditySensor;

public class SensorFactory {

    private SensorFactory() {

    }

    public static Sensor createSensor(final ConfigSensor config) {
        switch (config.getType()) {
            case temperature:
                return new TemperatureSensor(config.getUid(), config.getTopic());
            case temperature_humidity:
                return new TemperatureHumiditySensor(config.getUid(), config.getTopic());
            case counter:
                return new Counter(config.getUid(), config.getTopic(), config.getKey());
            default:
                throw new IllegalArgumentException("Should not reach this. Type is not set in configuration and default value is not available");
        }
    }
}
