package org.example.service.docker;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.service.wait.ExecutionWaitStrategy;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.MountableFile;

import java.util.function.Consumer;

import static org.example.service.utility.ServiceConstants.*;

@ApplicationScoped
public class DockerService {


    public GenericContainer<?> getContainer(String language, String version, String clonedProjectPath,
                                            String workingDir, Consumer<OutputFrame> consumer, String command) {
        DockerImageFactory factory = switch (language.toUpperCase()) {
            case JAVA -> new JavaDockerImageFactory();
            case C_SHARP -> new CSharpDockerImageFactory();
            default -> throw new IllegalStateException("Unsupported language: " + language);
        };
        ImageFromDockerfile image = factory.createImage(version);
        return createContainer(image, clonedProjectPath, workingDir, consumer, command);
    }

    private GenericContainer<?> createContainer(ImageFromDockerfile image, String clonedProjectPath, String workingDir,
                                               Consumer<OutputFrame> consumer, String command) {
        return new GenericContainer<>(image)
                .withCopyFileToContainer(MountableFile.forHostPath(clonedProjectPath), workingDir)
                .withWorkingDirectory(workingDir)
                .withLogConsumer(consumer)
                .withCommand(command)
                .waitingFor(new ExecutionWaitStrategy().withCommand(command));
    }
}