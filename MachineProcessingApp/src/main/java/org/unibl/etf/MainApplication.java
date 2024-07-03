package org.unibl.etf;

import org.unibl.etf.Model.Machine.Machine;

import org.unibl.etf.Util.PropertiesService;
import org.unibl.etf.Util.ThreadUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {
    public static AtomicBoolean stopped = new AtomicBoolean(false);
    private static final String STOP_COMMAND = "STOP";

    static {
        try {
            Handler fileHandler = new FileHandler(PropertiesService.getLogFilePath(), true);
            Logger.getLogger(MainApplication.class.getName()).setUseParentHandlers(false);
            Logger.getLogger(MainApplication.class.getName()).addHandler(fileHandler);
        } catch (IOException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.fillInStackTrace().toString());
        }
    }

    public static void main(String[] args) {
        Machine moldingMachine = new Machine(1);
        moldingMachine.start();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!stopped.get()) {
                ThreadUtil.sleep(1000);
                if (scanner.hasNext()) {
                    if (scanner.next().equals(STOP_COMMAND)) {
                        stopped.getAndSet(true);
                        break;
                    }
                }
            }
        }).start();
    }
}
