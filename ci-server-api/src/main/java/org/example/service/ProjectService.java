package org.example.service;

import io.quarkus.websockets.next.OpenConnections;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.domain.Build;
import org.example.domain.BuildStatus;
import org.example.domain.Project;
import org.example.repository.BuildRepository;
import org.example.repository.ProjectRepository;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.example.service.utility.LoggingUtility.BUILD_LOGS_DIR;
import static org.example.service.utility.LoggingUtility.LOG_FILENAME_PATTERN;

@ApplicationScoped
public class ProjectService {

    private final OpenConnections connections;
    private final ProjectRepository projectRepository;
    private final BuildRepository buildRepository;

    @Inject
    public ProjectService(ProjectRepository projectRepository, BuildRepository buildRepository, OpenConnections connections) {
        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
        this.connections = connections;
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findByIdOptional(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll().list();
    }

    @Transactional
    public Optional<List<Build>> getAllBuilds(Long id) {
        return findById(id).map(Project::getBuilds).map(List::copyOf);
    }

    @Transactional
    public Project save(Project project) {
        projectRepository.persist(project);
        return project;
    }

    @Transactional
    public Build createBuild(Long projectId, String branch) {
        var project = findById(projectId).orElseThrow(IllegalArgumentException::new);
        var build = Build.builder()
                .buildStatus(BuildStatus.RUNNING)
                .branch(branch)
                .build();
        project.addBuild(build);
        buildRepository.persist(build);

        build.setLogFilePath(Path.of(BUILD_LOGS_DIR, LOG_FILENAME_PATTERN.formatted(build.getId())).toString());
        buildRepository.persist(build);

        return build;
    }
}
