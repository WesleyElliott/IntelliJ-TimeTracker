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

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        if (Stopwatch.getInstance(e.getProject().getName()).isRunning()) {
            e.getPresentation().setEnabled(false);
        } else {
            e.getPresentation().setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        NotificationUtil.showNotification("Started logging for task - " + RepoHelper.getInstance().getCurrentBranch(getEventProject(e)), getEventProject(e));

        if (Stopwatch.getInstance(e.getProject().getName()).hasStarted()) {
            Stopwatch.getInstance(e.getProject().getName()).resumeTimer();
        } else {
            Stopwatch.getInstance(e.getProject().getName()).startTimer();
        }
    }


}
