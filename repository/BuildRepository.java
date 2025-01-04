package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.domain.Build;

@ApplicationScoped
public class BuildRepository implements PanacheRepository<Build> {
}
