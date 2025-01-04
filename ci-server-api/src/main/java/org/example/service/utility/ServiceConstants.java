package org.example.service.utility;

public class ServiceConstants {
    // Common
    public static final String CLONED_PROJECTS_PATH = "C:/programming/ci-server-api/tmp-projects";
    public static final String WORKFLOW_FILENAME = "workflow.yml";

    // Docker
    public static final String JAVA = "JAVA";
    public static final String C_SHARP = "C#";

    public static final String JAVA_DOCKERFILE_PATH = "C:/programming/ci-server-api/docker-envs/Dockerfile-java-amazon-corretto";
    public static final String JAVA_VERSION_ARG = "JAVA_VERSION";

    public static final String C_SHARP_DOCKERFILE_PATH = "C:/programming/ci-server-api/docker-envs/Dockerfile-c-sharp";
    public static final String C_SHARP_VERSION_ARG = "C_SHARP_VERSION";

    public static final String WORKSPACE_DIR_PATTERN = "/workspace/%s";
}
