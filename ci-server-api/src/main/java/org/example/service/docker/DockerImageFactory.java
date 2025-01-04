package org.example.service.docker;

import org.testcontainers.images.builder.ImageFromDockerfile;

public interface DockerImageFactory {
    ImageFromDockerfile createImage(String version);
}
