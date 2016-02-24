package com.wesleyelliott.timetracker.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Wesley on 2016/02/24.
 */
@com.intellij.openapi.components.State(
        name = "TimeTracker.Application.Settings",
        storages = {        @Storage(
                file = "$APP_CONFIG$/timeTracker.xml"
        )}
)
public class TimeTrackerAppSettings implements PersistentStateComponent<TimeTrackerAppSettings.State> {

    private TimeTrackerAppSettings.State myState = new TimeTrackerAppSettings.State();

    public TimeTrackerAppSettings() {
    }

    public static TimeTrackerAppSettings getInstance() {
        return (TimeTrackerAppSettings) ServiceManager.getService(TimeTrackerAppSettings.class);
    }

    @Nullable
    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(State state) {
        this.myState = state;
    }

    public void setAutoSave(boolean autoSave) {
        this.myState.autoSave = autoSave;
    }

    public void setIdleTimeLimit(int idleTimeLimit) {
        this.myState.idleTimeLimit = idleTimeLimit;
    }

    public void setAutoSaveTime(int autoSaveTime) {
        this.myState.autoSaveTime = autoSaveTime;
    }

    public boolean getAutoSave() {
        return myState.autoSave;
    }

    public int getIdleTimeLimit() {
        return (int) myState.idleTimeLimit;
    }

    public int getAutoSaveTime() {
        return (int) myState.autoSaveTime;
    }

    public static class State {
        public boolean autoSave = true;
        public long autoSaveTime = 1800000;
        public long idleTimeLimit = 300000;

        public State() {
        }
    }
}
