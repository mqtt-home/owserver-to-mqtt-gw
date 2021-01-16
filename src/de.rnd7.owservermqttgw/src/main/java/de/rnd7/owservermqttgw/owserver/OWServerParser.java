package de.rnd7.owservermqttgw.owserver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OWServerParser {
    private static final Pattern PATTERN = Pattern.compile("<b>([a-z0-9_]+)</b></td><td>(\\d+\\.?\\d*)</td>",
            Pattern.CASE_INSENSITIVE);

    private OWServerParser() {

    }

    static Map<String, Double>parse(String html) {
        Matcher matcher = PATTERN.matcher(html);
        Map<String, Double> result = new HashMap<>();

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            result.put(key, Double.parseDouble(value));
        }

        return result;
    }
}
