package de.rnd7.owservermqttgw.owserver;

import de.rnd7.mqttgateway.Events;
import de.rnd7.mqttgateway.PublishMessage;
import de.rnd7.owservermqttgw.config.ConfigOwServer;
import de.rnd7.owservermqttgw.config.ConfigSensor;
import de.rnd7.owservermqttgw.messages.SensorMessageFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OWServerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OWServerService.class);

    private final ConfigOwServer config;
    private final Collection<Sensor> sensors;

    public OWServerService(final ConfigOwServer config) {
        this.config = config;
        this.sensors = convertSensors(config.getSensors());
    }

    private static Collection<Sensor> convertSensors(final List<ConfigSensor> sensors) {
        return sensors.stream()
            .map(sensor -> new Sensor(sensor.getUid(), sensor.getTopic()))
            .collect(Collectors.toList());
    }

    public void start(final Duration pollingInterval) {
        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::exec,
            0,
            pollingInterval.getSeconds(),
            TimeUnit.SECONDS);
    }

    private void exec() {
        for (final Sensor sensor : sensors) {
            try {
                final Map<String, Double> data = readSensor(this.config.getUrl(), sensor);
                final Double temperature = data.get("temperature");
                final Double humidity = data.get("humidity");

                if (isValidTemperature(temperature)) {
                    Events.post(createMessage(sensor, temperature, humidity));
                }
            } catch (IOException e) {
                LOGGER.error("Error reading sensor: {} {}", sensor.getUuid(), e.getMessage());
            }
        }
    }

    private boolean isValidTemperature(final Double temperature) {
        return temperature != null && temperature > -199;
    }

    private PublishMessage createMessage(final Sensor sensor, final Double temperature, final Double humidity) {
        return SensorMessageFactory.create(sensor.getTopic(), temperature, humidity);
    }

    private static Map<String, Double> readSensor(final String rootUrl, final Sensor sensor) throws IOException {
        try {
            final URL url = new URL(String.format("%s/%s", rootUrl, sensor.getUuid()));
            try (InputStream in = url.openStream()) {
                return OWServerParser.parse(IOUtils.toString(in, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}
