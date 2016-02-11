package com.wesleyelliott.timetracker.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.util.messages.MessageBusConnection;
import java.util.concurrent.atomic.AtomicBoolean;

import com.wesleyelliott.timetracker.ProjectAware;
import org.jetbrains.annotations.NotNull;

public class StatusBarManager implements Disposable, ProjectAware {
    private final AtomicBoolean opened = new AtomicBoolean();
    private final Project myProject;
    private final MessageBusConnection myConnection;
    private TimeLogWidget myStatusWidget;

    private StatusBarManager(Project project) {
        myProject = project;
        myConnection = myProject.getMessageBus().connect();
    }

    public static StatusBarManager create(@NotNull Project project) {
        return new StatusBarManager(project);
    }

    private void install() {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(myProject);
        if (statusBar != null) {
            statusBar.addWidget(myStatusWidget, myProject);
            myStatusWidget.installed();
        }
    }

    private void uninstall() {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(myProject);
        if (statusBar != null) {
            statusBar.removeWidget(myStatusWidget.ID());
            myStatusWidget.uninstalled();
        }
    }

    @Override
    public void opened() {
        if (opened.compareAndSet(false, true)) {
            if (!ApplicationManager.getApplication().isHeadlessEnvironment()) {
                myStatusWidget = TimeLogWidget.create(myProject);
                install();
            }
        }
    }

    @Override
    public void closed() {
        opened.compareAndSet(true, false);
    }

    @Override
    public void dispose() {
        if (!ApplicationManager.getApplication().isHeadlessEnvironment()) {
            if (myStatusWidget != null) {
                uninstall();
                myStatusWidget = null;
            }
        }
        myConnection.disconnect();
    }
}
