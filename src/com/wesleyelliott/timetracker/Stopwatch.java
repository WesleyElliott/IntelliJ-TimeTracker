package com.wesleyelliott.timetracker;

import org.apache.commons.lang.time.StopWatch;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wesley on 2016/02/10.
 */
public class Stopwatch extends TimerTask {
    private static Stopwatch ourInstance = new Stopwatch();

    public interface TimeUpdateListener {
        void onTimeUpdated(long elapsedTime);
    }

    private StopWatch stopWatch;
    private boolean isRunning;
    private boolean hasStarted;
    private Timer timer;
    private TimeUpdateListener timeUpdateListener;
    private long elapsedTime = 0;

    public static Stopwatch getInstance() {
        return ourInstance;
    }

    private Stopwatch() {
        stopWatch = new StopWatch();
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

    public long getElapsedTime() {
        return elapsedTime + stopWatch.getTime();
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
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

    @Override
    public void run() {
        if (timeUpdateListener != null) {
            timeUpdateListener.onTimeUpdated(getElapsedTime());
        }
    }
}
