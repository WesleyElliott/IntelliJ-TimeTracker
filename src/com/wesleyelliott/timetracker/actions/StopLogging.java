package com.wesleyelliott.timetracker.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wesleyelliott.timetracker.util.NotificationUtil;
import com.wesleyelliott.timetracker.util.RepoHelper;
import com.wesleyelliott.timetracker.Stopwatch;

/**
 * Created by Wesley on 2016/02/10.
 */
public class StopLogging extends AnAction {

    private Stopwatch stopwatch = Stopwatch.getInstance();

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        if (stopwatch.isRunning()) {
            e.getPresentation().setEnabled(true);
        } else {
            e.getPresentation().setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        NotificationUtil.showNotification("Stopped logging for task - " + RepoHelper.getInstance().getCurrentBranch(getEventProject(e)));
        stopwatch.pauseTimer();
    }
}
