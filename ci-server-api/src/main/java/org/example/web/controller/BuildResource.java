package org.example.web.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.domain.Build;
import org.example.service.BuildService;
import org.example.web.dto.BuildDto;
import org.example.web.mapper.BuildMapper;
import org.jboss.resteasy.reactive.RestResponse;

import java.io.File;

@Path("/builds")
public class BuildResource {
    private final BuildService buildService;
    private final BuildMapper buildMapper;

    @Inject
    public BuildResource(BuildService buildService, BuildMapper buildMapper) {
        this.buildService = buildService;
        this.buildMapper = buildMapper;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<BuildDto> findById(@PathParam("id") Long id) {
        var build = buildService.findById(id);
        return build
                .map(buildMapper::toDto)
                .map(RestResponse::ok)
                .orElse(RestResponse.notFound());
    }

    @GET
    @Path("{id}/logs")
    public RestResponse<File> getLogs(@PathParam("id") Long id) {
        return buildService.findById(id)
                .map(Build::getLogFilePath)
                .map(File::new)
                .map(RestResponse::ok)
                .orElse(RestResponse.notFound());
    }
}
