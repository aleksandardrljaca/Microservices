package org.unibl.etf.Util;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {
    public static Integer extractPlateIdFromJSON(String json) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return null;
    }

    public static String generateTelemetryDataJSON(Integer machineId, Integer plateId, String type) {
        return "{\"machineId\":" + machineId + ",\"plateId\":" + plateId + ",\"time\":\"" +
                LocalTime.now().toString() + "\",\"type\":\"" + type + "\"}";
    }
}
