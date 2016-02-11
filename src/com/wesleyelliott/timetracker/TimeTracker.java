package com.wesleyelliott.timetracker;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.wesleyelliott.timetracker.ui.StatusBarManager;
import com.wesleyelliott.timetracker.util.FileUtil;
import com.wesleyelliott.timetracker.util.ProjectAwares;
import com.wesleyelliott.timetracker.util.StringUtil;

/**
 * Created by Wesley on 2016/02/11.
 */
public class TimeTracker extends AbstractProjectComponent {

    public TimeTracker(Project project) {
        super(project);
    }

    private ProjectAwares projectAwares;
    private StatusBarManager myStatusBarManager;

    @Override
    public void initComponent() {
        super.initComponent();
        myStatusBarManager = StatusBarManager.create(myProject);
        projectAwares = ProjectAwares.create(
            myStatusBarManager
        );
    }

    @Override
    public void disposeComponent() {
        myStatusBarManager.dispose();
        projectAwares.dispose();
    }

    @Override
    public void projectOpened() {
        projectAwares.opened();
        Long elapsedTime = FileUtil.getElapsedTime(myProject);

        System.out.println("Getting saved time: " + StringUtil.elapsedTimeToString(elapsedTime));
        Stopwatch.getInstance().setElapsedTime(elapsedTime);
    }

    @Override
    public void projectClosed() {
        Long elapsedTime = Stopwatch.getInstance().getElapsedTime();
        System.out.println("Saving time: " + StringUtil.elapsedTimeToString(elapsedTime));
        FileUtil.saveElapsedTime(myProject, elapsedTime);
        projectAwares.closed();
        Stopwatch.getInstance().restartTimer();
    }

}
