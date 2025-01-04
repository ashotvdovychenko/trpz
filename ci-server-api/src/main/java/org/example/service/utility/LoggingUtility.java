package org.example.service.utility;

import org.testcontainers.containers.output.OutputFrame;

import java.time.Instant;

public class LoggingUtility {
    public static final String BUILD_LOGS_DIR = "C:/programming/ci-server-api/build-logs";
    public static final String LOG_FILENAME_PATTERN = "log-%s.log";

    public static String formatLogEntry(OutputFrame outputFrame) {
        String timestamp = Instant.now().toString();
        return String.format("[%s] %s", timestamp, outputFrame.getUtf8String().trim());
    }
}
