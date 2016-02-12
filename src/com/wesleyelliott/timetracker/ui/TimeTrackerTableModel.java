package com.wesleyelliott.timetracker.ui;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.wesleyelliott.timetracker.util.FileUtil;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Wesley on 2016/02/12.
 */
public class TimeTrackerTableModel extends DefaultTableModel {

    private Project project;

    public TimeTrackerTableModel(Project project) {
        this.project = project;
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
        List<String> newHistory = new ArrayList<>();

        // Get Updated History
        for (Object row : getDataVector()) {
            Vector data = (Vector) row;
            String date = (String) data.get(0);
            String task = (String) data.get(1);
            String time = (String) data.get(2);
            newHistory.add(date + "," + task + "," + time);
        }

        // Clear Old History
        FileUtil.clearHistory(project);

        // Need to reverse list...
        newHistory = Lists.reverse(newHistory);
        for (String history : newHistory) {
            String[] data = history.split(",");

            FileUtil.saveTaskInHistory(project, data[1], data[0], data[2]);
        }
    }
}
