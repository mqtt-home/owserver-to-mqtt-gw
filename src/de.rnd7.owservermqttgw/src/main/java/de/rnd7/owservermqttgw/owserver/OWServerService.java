package de.rnd7.owservermqttgw.owserver;

import de.rnd7.owservermqttgw.config.ConfigOwServer;
import de.rnd7.owservermqttgw.config.ConfigSensor;
import de.rnd7.owservermqttgw.owserver.sensor.Sensor;
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
            .map(sensor -> SensorFactory.createSensor(sensor))
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
                final Map<String, String> data = readSensor(this.config.getUrl(), sensor);
                sensor.exec(data);
            } catch (IOException e) {
                LOGGER.error("Error reading sensor: {} {}", sensor.getUuid(), e.getMessage());
            }
        }
    }

    private static Map<String, String> readSensor(final String rootUrl, final Sensor sensor) throws IOException {
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
