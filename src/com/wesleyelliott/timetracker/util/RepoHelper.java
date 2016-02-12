package com.wesleyelliott.timetracker.util;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import git4idea.GitLocalBranch;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.apache.commons.lang.StringUtils;
import org.zmlx.hg4idea.repo.HgRepository;
import org.zmlx.hg4idea.repo.HgRepositoryManager;

/**
 * Created by Wesley on 2016/02/10.
 */
public class RepoHelper {

    private static RepoHelper ourInstance = new RepoHelper();

    private String currentBranch;

    public static RepoHelper getInstance() {
        return ourInstance;
    }

    private RepoHelper() {
    }

    public static String getHgBranch(Project project) {
        HgRepositoryManager repositoryManager = ServiceManager.getService(project, HgRepositoryManager.class);

        if (repositoryManager.getRepositories().size() > 0) {
            HgRepository hgRepository = repositoryManager.getRepositories().get(0);
            String branch = hgRepository.getCurrentBranch();
            if (StringUtils.isNotEmpty(branch)) {
                return branch;
            }
        }

        return StringUtils.EMPTY;
    }

    public static String getGitBranch(Project project) {
        GitRepositoryManager repositoryManager = ServiceManager.getService(project, GitRepositoryManager.class);

        if (repositoryManager.getRepositories().size() > 0) {
            GitRepository gitRepository = repositoryManager.getRepositories().get(0);
            GitLocalBranch branch = gitRepository.getCurrentBranch();
            if (branch != null) {
                return branch.getName();
            }
        }

        return StringUtils.EMPTY;
    }


    public String getCurrentBranch(Project project) {
        AbstractVcs[] VCS = ProjectLevelVcsManager.getInstance(project).getAllActiveVcss();
        if (VCS.length == 0) {
            return "";
        }

        AbstractVcs currentVersionControl = VCS[0];

        if (currentVersionControl.getName().equalsIgnoreCase("hg4idea")) {
            currentBranch = RepoHelper.getHgBranch(project);
        } else if (currentVersionControl.getName().equalsIgnoreCase("Git")) {
            currentBranch = RepoHelper.getGitBranch(project);
        }

        return currentBranch;
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public String getCurrentTask(Project project) {
        return project.getName() + " - " + getCurrentBranch(project);
    }

}
