package com.wesleyelliott.timetracker.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wesleyelliott.timetracker.Stopwatch;
import com.wesleyelliott.timetracker.ui.TimeTaskDialog;

/**
 * Created by Wesley on 2016/02/10.
 */
public class FinishTask extends AnAction {

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
        stopwatch.stopTimer();
        TimeTaskDialog.main(e.getProject());
    }
}
