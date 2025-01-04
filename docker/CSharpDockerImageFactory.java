package org.example.service.docker;

import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Paths;

import static org.example.service.utility.ServiceConstants.*;

public class CSharpDockerImageFactory implements DockerImageFactory {

    @Override
    public ImageFromDockerfile createImage(String version) {
        if (version == null || version.isEmpty()) {
            version = "7.0";
        }
        return new ImageFromDockerfile()
                .withDockerfile(Paths.get(C_SHARP_DOCKERFILE_PATH))
                .withBuildArg(C_SHARP_VERSION_ARG, version);
    }

}
