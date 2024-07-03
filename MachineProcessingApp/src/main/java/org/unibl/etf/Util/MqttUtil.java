package org.unibl.etf.Util;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.UUID;

public class MqttUtil {
    public static MqttClient configureClient() {
        MqttClient client;
        try {
            client = new MqttClient(PropertiesService.getBrokerURL(), UUID.randomUUID().toString());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return client;
    }
}
