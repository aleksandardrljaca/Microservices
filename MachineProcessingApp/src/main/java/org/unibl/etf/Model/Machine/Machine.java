package org.unibl.etf.Model.Machine;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.unibl.etf.MainApplication;
import org.unibl.etf.Model.Plate.Plate;
import org.unibl.etf.Util.*;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Machine extends Thread {
    protected Integer machineId;
    protected MqttClient client;

    public Machine(int id) {
        this.machineId = id;
        this.client = MqttUtil.configureClient();
    }

    private void publishTelemetryData(Integer plateId, String type, String machineTelemetryTopic) {
        String jsonString = JsonUtil.generateTelemetryDataJSON(machineId, plateId, type);
        try {
            client.publish(machineTelemetryTopic, new MqttMessage(jsonString.getBytes()));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private void publishZeroByteMessage(String topic) {
        try {
            client.publish(topic, new byte[0], 0, true);
        } catch (MqttException e) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void run() {
        //machine turned on
        publishZeroByteMessage(PropertiesService.getMachineTelemetryTopic());
        ThreadUtil.sleep(2000);

        publishTelemetryData(null, PropertiesService.getStartTelemetryType(), PropertiesService.getMachineTelemetryTopic());
        Logger.getLogger(MainApplication.class.getName()).log(Level.INFO, "MACHINE TURNED ON");
        System.out.println("MACHINE STARTED");
        while (!MainApplication.stopped.get()) {
            ThreadUtil.sleep(4000);
            Plate plate = new Plate();
            // store plate to db
            // products microservice endpoint
            HttpUtil.sendHttpPostRequest(PropertiesService.getPlatesPostEndpoint(), plate.toString());
            System.out.println("MOLDING MACHINE CREATED A NEW PLATE :" + plate.getId());
            ThreadUtil.sleep(500);
            // publishing plate creation started telemetry data
            publishTelemetryData(plate.getId(), PropertiesService.getMoldingPlateStartEvent(), PropertiesService.getMachineTelemetryTopic());

            // publishing plate created telemetry data
            publishTelemetryData(plate.getId(), PropertiesService.getMoldingPlateEndEvent(), PropertiesService.getMachineTelemetryTopic());

        }
        // machine turned off
        publishTelemetryData(null, PropertiesService.getEndTelemetryType(), PropertiesService.getMachineTelemetryTopic());
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        Logger.getLogger(MainApplication.class.getName()).log(Level.INFO, "MACHINE TURNED OFF");
    }

}
