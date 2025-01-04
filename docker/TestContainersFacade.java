package org.example.service.docker;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.service.docker.DockerService;
import org.example.service.wait.ExecutionWaitStrategy;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.MountableFile;

import java.util.function.Consumer;
import org.testcontainers.containers.output.OutputFrame;

@ApplicationScoped
public class TestContainersFacade {

    private final DockerService dockerService;

    @Inject
    public TestContainersFacade(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    /**
     * Створення контейнера для виконання команди.
     *
     * @param language     - мова програмування
     * @param version      - версія мови
     * @param command      - команда для виконання
     * @param projectPath  - шлях до проекту
     * @param workingDir   - робоча директорія в контейнері
     * @param consumer     - логування виводу
     * @return Контейнер для подальшого управління
     */
    public GenericContainer<?> createAndStartContainer(String language, String version, String command, String projectPath,
                                                       String workingDir, Consumer<OutputFrame> consumer) {
        GenericContainer<?> container = dockerService.getContainer(language, version, projectPath, workingDir, consumer, command);

        container.start();
        return container;
    }

    /**
     * Зупинити контейнер.
     *
     * @param container Контейнер для зупинки
     */
    public void stopContainer(GenericContainer<?> container) {
        if (container != null && container.isRunning()) {
            container.stop();
        }
    }

    /**
     * Додати файл до контейнера.
     *
     * @param container     Контейнер
     * @param sourcePath    Шлях на хості
     * @param destination   Шлях у контейнері
     */
    public void copyFileToContainer(GenericContainer<?> container, String sourcePath, String destination) {
        container.copyFileToContainer(MountableFile.forHostPath(sourcePath), destination);
    }

    /**
     * Очікувати виконання команди в контейнері.
     *
     * @param container Контейнер
     * @param command   Команда для виконання
     */
    public void waitForCommandExecution(GenericContainer<?> container, String command) {
        container.waitingFor(new ExecutionWaitStrategy().withCommand(command));
    }
}
