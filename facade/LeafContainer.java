package org.example.service.facade;

import org.testcontainers.containers.GenericContainer;

public class LeafContainer implements ContainerComponent {
    private final GenericContainer<?> container;

    public LeafContainer(GenericContainer<?> container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.start();
    }

    @Override
    public void stop() {
        container.stop();
    }
}
