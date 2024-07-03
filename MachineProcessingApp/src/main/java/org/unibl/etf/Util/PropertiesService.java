package org.unibl.etf.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {
    private static Properties properties;
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/config.properties";

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getBrokerURL() {
        return properties.getProperty("BROKER");
    }

    public static String getMachineTelemetryTopic() {
        return properties.getProperty("MACHINE_TELEMETRY_TOPIC");
    }

    public static String getStartTelemetryType() {
        return properties.getProperty("START_WORKING_TELEMETRY_TYPE");
    }

    public static String getEndTelemetryType() {
        return properties.getProperty("END_WORKING_TELEMETRY_TYPE");
    }

    public static String getMoldingPlateStartEvent() {
        return properties.getProperty("MOLDING_PLATE_START_EVENT");
    }

    public static String getMoldingPlateEndEvent() {
        return properties.getProperty("MOLDING_PLATE_END_EVENT");
    }

    public static String getPlatesPostEndpoint() {
        return properties.getProperty("PLATES_POST_ENDPOINT");
    }

    public static String getLogFilePath() {
        return properties.getProperty("LOG_FILE_PATH");
    }

    public static String getLastPlateIdEndpointUrl() {
        return properties.getProperty("LAST_PLATE_ID_URL");
    }
}
