package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.domain.Project;

import java.util.Optional;

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {

    public Optional<Project> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
}
