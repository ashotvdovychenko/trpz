package org.example.web.mapper;

import org.example.domain.Build;
import org.example.web.dto.BuildDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface BuildMapper {

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "startTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "endTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    BuildDto toDto(Build build);
}
