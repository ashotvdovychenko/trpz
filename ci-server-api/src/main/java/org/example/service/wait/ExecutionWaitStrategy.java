package org.example.service.wait;

import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.wait.strategy.AbstractWaitStrategy;

public class ExecutionWaitStrategy extends AbstractWaitStrategy {
    private String command;

    public ExecutionWaitStrategy withCommand(String command) {
        this.command = command;
        return this;
    }

    @Override
    protected void waitUntilReady() {
        int exitCode = -1;
        try {
            exitCode = waitStrategyTarget.execInContainer("/bin/sh", "-c", this.command).getExitCode();
            System.out.println(exitCode);
            if (exitCode != 0 && exitCode != 137) {
                throw new RuntimeException("Command execution failed: " + exitCode);
            }
        } catch (Exception e) {
            throw new ContainerLaunchException("Command execution failed! %s finished with exit code %s".formatted(command, exitCode), e);
        }
    }
}
