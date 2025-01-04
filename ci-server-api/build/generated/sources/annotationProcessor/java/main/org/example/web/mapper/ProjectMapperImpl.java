package org.example.web.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import javax.annotation.processing.Generated;
import org.example.domain.Project;
import org.example.web.dto.ProjectCreateDto;
import org.example.web.dto.ProjectDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-04T07:45:32+0200",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.9.jar, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@ApplicationScoped
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectDto toDto(Project project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String repositoryUrl = null;

        id = project.getId();
        name = project.getName();
        repositoryUrl = project.getRepositoryUrl();

        ProjectDto projectDto = new ProjectDto( id, name, repositoryUrl );

        return projectDto;
    }

    @Override
    public Project toEntity(ProjectCreateDto projectDto) {
        if ( projectDto == null ) {
            return null;
        }

        Project project = new Project();

        project.setName( projectDto.name() );
        project.setRepositoryUrl( projectDto.repositoryUrl() );

        return project;
    }
}
