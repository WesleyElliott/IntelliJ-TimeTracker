package com.wesleyelliott.timetracker.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.util.Consumer;
import com.wesleyelliott.timetracker.util.FileUtil;
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

    private String text = StringUtil.elapsedTimeToString(0);
    private final AtomicBoolean opened = new AtomicBoolean();

    public TimeLogWidget(@NotNull Project project) {
        super(project);
        Stopwatch.getInstance().setOnTimeUpdateListener(this);
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
                for (String line : FileUtil.getTaskHistory(myProject)) {
                    System.out.println(line);
                }
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

    public void installed() {
        opened.compareAndSet(false, true);
    }

    public void uninstalled() {
        opened.compareAndSet(true, false);
    }
}
