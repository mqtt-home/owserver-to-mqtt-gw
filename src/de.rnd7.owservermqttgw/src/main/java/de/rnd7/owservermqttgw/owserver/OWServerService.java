package de.rnd7.owservermqttgw.owserver;

import com.google.common.base.Objects;
import de.rnd7.owservermqttgw.Events;
import de.rnd7.owservermqttgw.config.ConfigOwServer;
import de.rnd7.owservermqttgw.config.ConfigSensor;
import de.rnd7.owservermqttgw.messages.Message;
import de.rnd7.owservermqttgw.messages.SensorMessage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OWServerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OWServerService.class);

    private final ConfigOwServer config;
    private final boolean deduplicate;
    private final Collection<Sensor> sensors;
    private final Map<String, String> deduplicationInfo = new HashMap<>();

    public OWServerService(final ConfigOwServer config, final boolean deduplicate) {
        this.config = config;
        this.deduplicate = deduplicate;
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
                    final Message message = createMessage(sensor, temperature, humidity);
                    if (handleDeduplication(sensor.getUuid(), message)) {
                        Events.post(message);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Error reading sensor: {} {}", sensor.getUuid(), e.getMessage());
            }
        }
    }

    boolean handleDeduplication(final String uid, final Message message) {
        if (!deduplicate) {
            return true;
        }

        final String value = message.getValueString();
        final String oldValue = deduplicationInfo.get(uid);
        this.deduplicationInfo.put(uid, value);

        return !Objects.equal(oldValue, value);
    }

    private boolean isValidTemperature(final Double temperature) {
        return temperature != null && temperature > -199;
    }

    private Message createMessage(final Sensor sensor, final Double temperature, final Double humidity) {
        return new SensorMessage(sensor.getTopic(), temperature, humidity);
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
