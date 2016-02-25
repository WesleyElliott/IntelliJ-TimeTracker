package com.wesleyelliott.timetracker;

import com.wesleyelliott.timetracker.settings.TimeTrackerAppSettings;
import com.wesleyelliott.timetracker.util.FileUtil;
import org.apache.commons.lang.time.StopWatch;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wesley on 2016/02/10.
 */
public class Stopwatch extends TimerTask {
    private static HashMap<String, Stopwatch> instances = new HashMap<>();

    public interface TimeUpdateListener {
        void onTimeUpdated(long elapsedTime);
        void onSaveTime(long time);
    }

    private StopWatch stopWatch;
    private StopWatch saveWatch;
    private boolean isRunning;
    private boolean hasStarted;
    private Timer timer;
    private TimeUpdateListener timeUpdateListener;
    private long elapsedTime = 0;
    private long startTime = 0;
    private long discardedTime = 0;

    public static Stopwatch getInstance(String projectName) {
        if (instances.get(projectName) == null) {
            instances.put(projectName, new Stopwatch());
        }
        return instances.get(projectName);
    }

    private Stopwatch() {
        stopWatch = new StopWatch();
        saveWatch = new StopWatch();
        isRunning = false;
        hasStarted = false;
        timer = new Timer();
        timer.schedule(this, 0, 1000);
    }

    public void startTimer() {
        stopWatch.start();
        isRunning = true;
        hasStarted = true;
    }

    public void stopTimer() {
        if (isRunning) {
            stopWatch.stop();
            timer.cancel();
            timer.purge();
        }
        isRunning = false;
    }

    public void pauseTimer() {
        stopWatch.suspend();
        isRunning = false;
    }

    public void resumeTimer() {
        stopWatch.resume();
        isRunning = true;
    }

    public void restartTimer() {
        stopWatch.reset();
        isRunning = false;
        hasStarted = false;
        elapsedTime = 0;
    }

    public void clearTimes() {
        startTime = 0;
        discardedTime = 0;
    }

    public void startSaveWatch() {
        saveWatch.start();
    }

    public void stopSaveWatch() {
        saveWatch.stop();
        saveWatch.reset();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.startTime = elapsedTime;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public void setOnTimeUpdateListener(TimeUpdateListener listener) {
        this.timeUpdateListener = listener;
    }

    public void addDiscardedTime(long discardedTime) {
        this.discardedTime = this.discardedTime + discardedTime;
    }

    @Override
    public void run() {
        elapsedTime = startTime + stopWatch.getTime() - discardedTime;
        if (timeUpdateListener != null) {
            timeUpdateListener.onTimeUpdated(getElapsedTime());
        }

        if (TimeTrackerAppSettings.getInstance().getAutoSave()) {
            if (saveWatch.getTime() >= TimeTrackerAppSettings.getInstance().getAutoSaveTime()) {
                timeUpdateListener.onSaveTime(getElapsedTime());
                saveWatch.reset();
                saveWatch.start();
            }
        }
    }
}
