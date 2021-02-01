package de.rnd7.owservermqttgw.owserver;

import de.rnd7.owservermqttgw.config.ConfigSensor;
import de.rnd7.owservermqttgw.owserver.sensor.Sensor;
import de.rnd7.owservermqttgw.owserver.sensor.Counter;
import de.rnd7.owservermqttgw.owserver.sensor.TemperatureSensor;
import de.rnd7.owservermqttgw.owserver.sensor.TemperatureHumiditySensor;

public class SensorFactory {
    public static Sensor createSensor(final ConfigSensor config) {
        final String type = config.getType();

        if (type.equalsIgnoreCase("temperaturehumidity")) {
            return new TemperatureHumiditySensor(config.getUid(), config.getTopic());
        }
        else if (type.equalsIgnoreCase("temperature")) {
            return new TemperatureSensor(config.getUid(), config.getTopic());
        }
        else if (type.equalsIgnoreCase("counter")) {
            return new Counter(config.getUid(), config.getTopic(), config.getKey());
        }
        throw new IllegalArgumentException("Should not reach this. Type is not set in configuration and default value is not available");
    }
}