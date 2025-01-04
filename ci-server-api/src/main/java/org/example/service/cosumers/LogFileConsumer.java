package org.example.service.cosumers;

import org.example.service.utility.LoggingUtility;
import org.testcontainers.containers.output.OutputFrame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class LogFileConsumer implements Consumer<OutputFrame> {
    private final String id;
    private final String logFileName;

    public LogFileConsumer(String id, String logFileName) {
        this.id = id;
        this.logFileName = logFileName;
    }

    @Override
    public void accept(OutputFrame outputFrame) {
        String logMessage = LoggingUtility.formatLogEntry(outputFrame);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write log for id: " + id);
        }
    }
}
