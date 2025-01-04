package org.example.service.facade;

import java.util.ArrayList;
import java.util.List;

public class ContainerGroup implements ContainerComponent {
    private final List<ContainerComponent> containers = new ArrayList<>();

    public void add(ContainerComponent component) {
        containers.add(component);
    }

    public void remove(ContainerComponent component) {
        containers.remove(component);
    }

    @Override
    public void start() {
        for (ContainerComponent container : containers) {
            container.start();
        }
    }

    @Override
    public void stop() {
        for (ContainerComponent container : containers) {
            container.stop();
        }
    }
}
