package com.wesleyelliott.timetracker.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by Wesley on 2016/02/24.
 */
public class TimeTrackerConfigurable implements Configurable {

    public static final String DISPLAY_NAME = "TimeTracker";

    private PreferencesPanel prefsPanel;

    @Nls
    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        prefsPanel = new PreferencesPanel();
        prefsPanel.load();
        return prefsPanel.getPanel();
    }

    @Override
    public boolean isModified() {
        return prefsPanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        prefsPanel.save();
    }

    @Override
    public void reset() {
        prefsPanel.load();
    }

    @Override
    public void disposeUIResources() {

    }
}
