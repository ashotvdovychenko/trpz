package org.example.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "BUILDS")
public class Build {
    @Id
    @GeneratedValue
    private Long id;

    private Instant startTime = Instant.now();

    private Instant endTime;

    private String logFilePath;

    private String branch;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "build_status")
    private BuildStatus buildStatus;

    public Build(Long id, Instant startTime, Instant endTime, String logFilePath, String branch, Project project, BuildStatus buildStatus) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.logFilePath = logFilePath;
        this.branch = branch;
        this.project = project;
        this.buildStatus = buildStatus;
    }

    public Build() {
    }

    public static BuildBuilder builder() {
        return new BuildBuilder();
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public static class BuildBuilder {
        private Long id;
        private Instant startTime;
        private Instant endTime;
        private String logFilePath;
        private String branch;
        private Project project;
        private BuildStatus buildStatus;

        BuildBuilder() {
        }

        public BuildBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BuildBuilder startTime(Instant startTime) {
            this.startTime = startTime;
            return this;
        }

        public BuildBuilder endTime(Instant endTime) {
            this.endTime = endTime;
            return this;
        }

        public BuildBuilder logFilePath(String logFilePath) {
            this.logFilePath = logFilePath;
            return this;
        }

        public BuildBuilder branch(String branch) {
            this.branch = branch;
            return this;
        }

        public BuildBuilder project(Project project) {
            this.project = project;
            return this;
        }

        public BuildBuilder buildStatus(BuildStatus buildStatus) {
            this.buildStatus = buildStatus;
            return this;
        }

        public Build build() {
            return new Build(this.id, this.startTime, this.endTime, this.logFilePath, this.branch, this.project, this.buildStatus);
        }

        public String toString() {
            return "Build.BuildBuilder(id=" + this.id + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", logFilePath=" + this.logFilePath + ", branch=" + this.branch + ", project=" + this.project + ", buildStatus=" + this.buildStatus + ")";
        }
    }
}
