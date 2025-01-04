package org.example.service.cosumers;

import io.quarkus.websockets.next.OpenConnections;
import org.example.service.utility.LoggingUtility;
import org.testcontainers.containers.output.OutputFrame;

import java.util.function.Consumer;

public class WebSocketConsumer implements Consumer<OutputFrame> {
    private final String id;
    private final OpenConnections connections;

    public WebSocketConsumer(String id, OpenConnections connections) {
        this.id = id;
        this.connections = connections;
    }

    @Override
    public void accept(OutputFrame outputFrame) {
        var cons = connections.stream()
                .filter(con -> id.equals(con.pathParam("id")))
                .toList();
        var message = LoggingUtility.formatLogEntry(outputFrame);
        cons.forEach(ws -> ws.sendTextAndAwait(message));
    }
}
