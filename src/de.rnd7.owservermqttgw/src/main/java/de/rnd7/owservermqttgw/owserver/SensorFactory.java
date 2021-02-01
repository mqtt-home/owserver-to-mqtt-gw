package de.rnd7.owservermqttgw.owserver;

import de.rnd7.owservermqttgw.config.ConfigSensor;
import de.rnd7.owservermqttgw.owserver.sensor.Sensor;
import de.rnd7.owservermqttgw.owserver.sensor.Counter;
import de.rnd7.owservermqttgw.owserver.sensor.TemperatureSensor;
import de.rnd7.owservermqttgw.owserver.sensor.TemperatureHumiditySensor;

public class SensorFactory {
    public static Sensor createSensor(final ConfigSensor config) {
        final String type = config.getType();

        if (type.equalsIgnoreCase("TemperatureHumidity")) {
            return new TemperatureHumiditySensor(config.getUid(), config.getTopic());
        }
        else if (type.equalsIgnoreCase("Temperature")) {
            return new TemperatureSensor(config.getUid(), config.getTopic());
        }
        else if (type.equalsIgnoreCase("Counter")) {
            return new Counter(config.getUid(), config.getTopic(), config.getKey());
        }
        return new TemperatureHumiditySensor(config.getUid(), config.getTopic());
    }
}