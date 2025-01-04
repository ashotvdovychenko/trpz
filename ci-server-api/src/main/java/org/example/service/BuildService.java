package org.example.service;

import io.quarkus.websockets.next.OpenConnections;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.domain.Build;
import org.example.domain.BuildStatus;
import org.example.domain.Project;
import org.example.repository.BuildRepository;
import org.example.service.cosumers.LogFileConsumer;
import org.example.service.cosumers.WebSocketConsumer;
import org.example.service.docker.DockerService;
import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.example.service.utility.ServiceConstants.WORKSPACE_DIR_PATTERN;

@ApplicationScoped
public class BuildService {

    private final OpenConnections connections;
    private final BuildRepository buildRepository;
    private final GitService gitService;
    private final DockerService dockerService;
    private final YamlWorkflowProcessor yamlWorkflowProcessor = YamlWorkflowProcessor.getInstance();

    @Inject
    public BuildService(BuildRepository buildRepository, OpenConnections connections,
                        GitService gitService, DockerService dockerService) {
        this.buildRepository = buildRepository;
        this.connections = connections;
        this.gitService = gitService;
        this.dockerService = dockerService;
    }

    public Optional<Build> findById(Long id) {
        return buildRepository.findByIdOptional(id);
    }

    @Transactional
    public void runProjectBuild(String id) {
        Build build = findById(Long.parseLong(id)).orElseThrow(IllegalArgumentException::new);
        Project project = build.getProject();
        var consumer = new WebSocketConsumer(id, connections).andThen(new LogFileConsumer(id, build.getLogFilePath()));
        var tmpProjectDirName = "%s-%s".formatted(project.getName(), UUID.randomUUID());
        var clonedProjectDir = gitService.cloneRepository(tmpProjectDirName, project.getRepositoryUrl(), build.getBranch());

        Map<String, Object> workflowData = yamlWorkflowProcessor.extractWorkflowData(clonedProjectDir);
        String command = yamlWorkflowProcessor.getCommandFromWorkflowData(workflowData);
        Map<String, String> withVariables = yamlWorkflowProcessor.getWithVariablesFromWorkflowData(workflowData);

        var workingDir = WORKSPACE_DIR_PATTERN.formatted(tmpProjectDirName);
        try (var container = dockerService.getContainer(withVariables.get("language"), withVariables.get("version"),
                clonedProjectDir, workingDir, consumer, command)) {
            container.start();
            build.setBuildStatus(BuildStatus.SUCCESS);
            build.setEndTime(Instant.now());
            buildRepository.persist(build);;
        } catch (ContainerLaunchException launchException) {
            build.setBuildStatus(BuildStatus.FAILURE);
            build.setEndTime(Instant.now());
            buildRepository.persist(build);;
        };
    }
}
