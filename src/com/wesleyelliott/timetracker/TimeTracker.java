package com.wesleyelliott.timetracker;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.wesleyelliott.timetracker.settings.TimeTrackerAppSettings;
import com.wesleyelliott.timetracker.ui.IdleTimeDialog;
import com.wesleyelliott.timetracker.ui.StatusBarManager;
import com.wesleyelliott.timetracker.util.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wesley on 2016/02/11.
 */
public class TimeTracker extends AbstractProjectComponent implements IdleTimeDialog.DiscardIdleTimeListener {

    public TimeTracker(Project project) {
        super(project);
    }

    private ProjectAwares projectAwares;
    private StatusBarManager myStatusBarManager;
    private boolean isIdleDialogShowing = false;

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
        Stopwatch.getInstance(myProject.getName()).setElapsedTime(elapsedTime);
        Stopwatch.getInstance(myProject.getName()).startSaveWatch();
        startIdleWatch();
    }

    @Override
    public void projectClosed() {
        Long elapsedTime = Stopwatch.getInstance(myProject.getName()).getElapsedTime();
        FileUtil.saveElapsedTime(myProject, elapsedTime);

        projectAwares.closed();
        Stopwatch.getInstance(myProject.getName()).restartTimer();
        Stopwatch.getInstance(myProject.getName()).stopSaveWatch();
    }

    private void startIdleWatch() {
        new Timer().schedule(timerTask, 0, 1000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            long idleTime = OSxIdleTime.getIdleTimeMillis();
            long idleTimeLimit = TimeTrackerAppSettings.getInstance().getIdleTimeLimit();
            if (idleTime > idleTimeLimit && Stopwatch.getInstance(myProject.getName()).isRunning()) {
                System.out.println("IDLE FOR " + StringUtil.elapsedTimeToString(idleTime));

                if (!isIdleDialogShowing) {
                    isIdleDialogShowing = true;
                    IdleTimeDialog.main(TimeTracker.this);
                }
            }
        }
    };

    @Override
    public void onDiscardTime(long idleTime) {
        System.out.println("Discarding Idle Time:  " + StringUtil.elapsedTimeToString(idleTime));
        Stopwatch.getInstance(myProject.getName()).addDiscardedTime(idleTime);
        Stopwatch.getInstance(myProject.getName()).pauseTimer();
        isIdleDialogShowing = false;
    }

    @Override
    public void onCancel() {
        isIdleDialogShowing = false;
    }
}
