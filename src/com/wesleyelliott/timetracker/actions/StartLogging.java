package com.wesleyelliott.timetracker.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.wesleyelliott.timetracker.util.NotificationUtil;
import com.wesleyelliott.timetracker.util.RepoHelper;
import com.wesleyelliott.timetracker.Stopwatch;

/**
 * Created by Wesley on 2016/02/10.
 */
public class StartLogging extends DumbAwareAction {

    private Stopwatch stopwatch = Stopwatch.getInstance();

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        if (stopwatch.isRunning()) {
            e.getPresentation().setEnabled(false);
        } else {
            e.getPresentation().setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        NotificationUtil.showNotification("Started logging for project - " + RepoHelper.getInstance().getCurrentBranch(getEventProject(e)));

        if (stopwatch.hasStarted()) {
            stopwatch.resumeTimer();
        } else {
            stopwatch.startTimer();
        }
    }


}
