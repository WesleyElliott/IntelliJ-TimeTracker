package com.wesleyelliott.timetracker.settings;

import com.wesleyelliott.timetracker.settings.TimeTrackerAppSettings;
import com.wesleyelliott.timetracker.util.StringUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Wesley on 2016/02/24.
 */
public class PreferencesPanel {

    private final TimeTrackerAppSettings myAppSettings;

    private JComponent myRootPanel;
    private JCheckBox autoSaveCheckBox;
    private JLabel autoSaveIntervalLabel;
    private JSpinner autoSaveIntervalSpinner;
    private JSpinner idleTimeSpinner;

    public PreferencesPanel() {
        this.myAppSettings = TimeTrackerAppSettings.getInstance();

        autoSaveCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                autoSaveIntervalLabel.setEnabled(autoSaveCheckBox.isSelected());
                autoSaveIntervalSpinner.setEnabled(autoSaveCheckBox.isSelected());
            }
        });
    }

    public JComponent getPanel() {
        return this.myRootPanel;
    }

    public void save() {
        myAppSettings.setAutoSave(autoSaveCheckBox.isSelected());
        myAppSettings.setIdleTimeLimit((Integer)idleTimeSpinner.getValue() * 60000);
        myAppSettings.setAutoSaveTime((Integer)autoSaveIntervalSpinner.getValue() * 60000);
    }

    public void load() {
        autoSaveCheckBox.setSelected(myAppSettings.getAutoSave());
        idleTimeSpinner.setValue(myAppSettings.getIdleTimeLimit() / 60000);
        autoSaveIntervalSpinner.setValue(myAppSettings.getAutoSaveTime() / 60000);
    }

    public boolean isModified() {
        int idleTime = (Integer) idleTimeSpinner.getValue();
        int autoSaveInterval = (Integer) autoSaveIntervalSpinner.getValue();

        return (!(autoSaveCheckBox.isSelected() == myAppSettings.getAutoSave()) ||
                !(idleTime == myAppSettings.getIdleTimeLimit() / 60000) ||
                !(autoSaveInterval == myAppSettings.getAutoSaveTime() / 60000));
    }
}
