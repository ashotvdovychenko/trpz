package org.example.web.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import javax.annotation.processing.Generated;
import org.example.domain.Build;
import org.example.domain.BuildStatus;
import org.example.domain.Project;
import org.example.web.dto.BuildDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-04T07:45:32+0200",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.9.jar, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@ApplicationScoped
public class BuildMapperImpl implements BuildMapper {

    @Override
    public BuildDto toDto(Build build) {
        if ( build == null ) {
            return null;
        }

        Long projectId = null;
        String startTime = null;
        String endTime = null;
        Long id = null;
        BuildStatus buildStatus = null;
        String branch = null;

        projectId = buildProjectId( build );
        if ( build.getStartTime() != null ) {
            startTime = build.getStartTime().toString();
        }
        if ( build.getEndTime() != null ) {
            endTime = build.getEndTime().toString();
        }
        id = build.getId();
        buildStatus = build.getBuildStatus();
        branch = build.getBranch();

        BuildDto buildDto = new BuildDto( id, startTime, endTime, buildStatus, projectId, branch );

        return buildDto;
    }

    private Long buildProjectId(Build build) {
        Project project = build.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getId();
    }
}
