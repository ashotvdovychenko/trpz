package org.example.web.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.example.domain.Project;
import org.example.service.BuildService;
import org.example.service.GitService;
import org.example.service.ProjectService;
import org.example.web.dto.BuildDto;
import org.example.web.dto.ProjectCreateDto;
import org.example.web.dto.ProjectDto;
import org.example.web.mapper.BuildMapper;
import org.example.web.mapper.ProjectMapper;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/projects")
public class ProjectResource {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final BuildMapper buildMapper;
    private final GitService gitService;

    @Inject
    public ProjectResource(ProjectService projectService, ProjectMapper projectMapper, BuildMapper buildMapper, GitService gitService) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.buildMapper = buildMapper;
        this.gitService = gitService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<ProjectDto> createProject(ProjectCreateDto projectDto, @Context UriInfo uriInfo) {
        var project = projectService.save(projectMapper.toEntity(projectDto));
        var uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(project.getId())).build();
        return RestResponse.seeOther(uri);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<ProjectDto>> getProjects() {
        var list = projectService.findAll().stream().map(projectMapper::toDto).toList();
        return RestResponse.ok(list);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<ProjectDto> findById(@PathParam("id") Long id) {
        var project = projectService.findById(id);
        return project
                .map(projectMapper::toDto)
                .map(RestResponse::ok)
                .orElse(RestResponse.notFound());
    }

    @GET
    @Path("{id}/branches")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<String>> getAllRemoteBranches(@PathParam("id") Long id) {
        var project = projectService.findById(id);
        return project
                .map(Project::getRepositoryUrl)
                .map(gitService::getAllRemoteBranches)
                .map(RestResponse::ok)
                .orElse(RestResponse.notFound());
    }



    @GET
    @Path("{id}/builds")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<BuildDto>> findAllBuilds(@PathParam("id") Long id) {
        var builds = projectService.getAllBuilds(id);
        return builds
                .map(list -> list.stream().map(buildMapper::toDto).toList())
                .map(RestResponse::ok)
                .orElse(RestResponse.notFound());
    }

    @POST
    @Path("{id}/builds")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<BuildDto> createNewBuild(@PathParam("id") Long id, @QueryParam("branch") String branch,
                                                 @Context UriInfo uriInfo) {
        var build = projectService.createBuild(id, branch);
        var uri = uriInfo.getBaseUriBuilder().path(BuildResource.class).path(String.valueOf(build.getId())).build();
        return RestResponse.seeOther(uri);
    }

}