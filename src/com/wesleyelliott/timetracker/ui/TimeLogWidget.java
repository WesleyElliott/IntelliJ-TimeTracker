package com.wesleyelliott.timetracker.ui;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.ui.content.impl.MessageViewImpl;
import com.intellij.util.Consumer;
import com.intellij.util.MessageBusUtil;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusFactory;
import com.intellij.util.messages.impl.Message;
import com.wesleyelliott.timetracker.util.FileUtil;
import com.wesleyelliott.timetracker.util.NotificationUtil;
import com.wesleyelliott.timetracker.util.RepoHelper;
import com.wesleyelliott.timetracker.Stopwatch;
import com.wesleyelliott.timetracker.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Wesley on 2016/02/11.
 */
public class TimeLogWidget extends EditorBasedWidget implements  StatusBarWidget.TextPresentation, Stopwatch.TimeUpdateListener {

    public static final String id = TimeLogWidget.class.getName();

    private String text = "";
    private final AtomicBoolean opened = new AtomicBoolean();

    public TimeLogWidget(@NotNull Project project) {
        super(project);
        Stopwatch.getInstance(project.getName()).setOnTimeUpdateListener(this);
        text= StringUtil.elapsedTimeToString(Stopwatch.getInstance(project.getName()).getElapsedTime());
    }

    public static TimeLogWidget create(@NotNull Project project) {
        return new TimeLogWidget(project);
    }

    @NotNull
    @Override
    public String ID() {
        return id;
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation(@NotNull PlatformType type) {
        return this;
    }

    @NotNull
    @Override
    public String getText() {
        return text;
    }

    @NotNull
    @Override
    public String getMaxPossibleText() {
        return "0000000000000000";
    }

    @Override
    public float getAlignment() {
        return Component.LEFT_ALIGNMENT;
    }

    @Nullable
    @Override
    public String getTooltipText() {
        return RepoHelper.getInstance().getCurrentBranch(myProject);
    }

    @Nullable
    @Override
    public Consumer<MouseEvent> getClickConsumer() {
        return new Consumer<MouseEvent>() {
            @Override
            public void consume(MouseEvent mouseEvent) {
                HistoryDialog.main(myProject);
            }
        };
    }

    @Override
    public void onTimeUpdated(long elapsedTime) {
        text = StringUtil.elapsedTimeToString(elapsedTime);
        if (myStatusBar != null) {
            myStatusBar.updateWidget(id);
        }
    }

    @Override
    public void onSaveTime(long time) {
        FileUtil.saveElapsedTime(myProject, time);
        NotificationUtil.LogNotification("Auto saving TimeTracker for " + myProject.getName(), myProject);
    }

    public void installed() {
        opened.compareAndSet(false, true);
    }

    public void uninstalled() {
        opened.compareAndSet(true, false);
    }
}
