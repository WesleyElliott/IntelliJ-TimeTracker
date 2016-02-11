package com.wesleyelliott.timetracker.util;

import com.google.common.collect.Lists;
import com.intellij.openapi.Disposable;
import com.wesleyelliott.timetracker.ProjectAware;

import java.util.Arrays;
import java.util.Collection;

public class ProjectAwares implements ProjectAware, Disposable {
    private final Collection<ProjectAware> myAwares;

    private ProjectAwares(Iterable<? extends ProjectAware> awares) {
        myAwares = Lists.newArrayList(awares);
    }

    public static ProjectAwares create(ProjectAware... awares) {
        return new ProjectAwares(Arrays.asList(awares));
    }

    @Override
    public void opened() {
        for (ProjectAware aware : myAwares) {
            aware.opened();
        }
    }

    @Override
    public void closed() {
        for (ProjectAware aware : myAwares) {
            aware.closed();
        }
    }

    @Override
    public void dispose() {
        myAwares.clear();
    }
}
