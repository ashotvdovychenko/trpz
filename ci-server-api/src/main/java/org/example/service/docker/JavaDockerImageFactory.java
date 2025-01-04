package org.example.service.docker;

import org.testcontainers.images.builder.ImageFromDockerfile;
import java.nio.file.Paths;

import static org.example.service.utility.ServiceConstants.JAVA_DOCKERFILE_PATH;
import static org.example.service.utility.ServiceConstants.JAVA_VERSION_ARG;

public class JavaDockerImageFactory implements DockerImageFactory {

    @Override
    public ImageFromDockerfile createImage(String version) {
        if (version == null || version.isEmpty()) {
            version = "21";
        }
        return new ImageFromDockerfile()
                .withDockerfile(Paths.get(JAVA_DOCKERFILE_PATH))
                .withBuildArg(JAVA_VERSION_ARG, version);
    }

}
