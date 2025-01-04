package org.example.web.mapper;

import org.example.domain.Project;
import org.example.web.dto.ProjectCreateDto;
import org.example.web.dto.ProjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toEntity(ProjectCreateDto projectDto);
}
