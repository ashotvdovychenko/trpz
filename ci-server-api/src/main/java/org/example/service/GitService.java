package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.example.service.utility.ServiceConstants;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class GitService {

    public String cloneRepository(String tmpProjectDirName, String repositoryUrl, String branch) {
        var path = Paths.get(ServiceConstants.CLONED_PROJECTS_PATH, tmpProjectDirName).toString();
        try (var ignored = Git.cloneRepository().setDirectory(new File(path)).setBranch(branch).setURI(repositoryUrl).call()) {
            return path;
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllRemoteBranches(String repositoryUrl) {
        try {
            Collection<Ref> remoteBranches = Git.lsRemoteRepository().setRemote(repositoryUrl).setHeads(true).call();
            return remoteBranches.stream().map(Ref::getName).toList();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
