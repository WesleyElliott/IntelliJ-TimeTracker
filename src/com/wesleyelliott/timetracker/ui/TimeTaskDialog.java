package com.wesleyelliott.timetracker.ui;

import com.intellij.openapi.project.Project;
import com.wesleyelliott.timetracker.TimeTracker;
import com.wesleyelliott.timetracker.util.FileUtil;
import com.wesleyelliott.timetracker.util.NotificationUtil;
import com.wesleyelliott.timetracker.util.RepoHelper;
import com.wesleyelliott.timetracker.Stopwatch;
import com.wesleyelliott.timetracker.util.StringUtil;

import javax.swing.*;
import java.awt.event.*;

public class TimeTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel timeElapsedLabel;
    private JLabel projectLabel;
    private JButton buttonNo;
    private RepoHelper repoHelper = RepoHelper.getInstance();

    private Project project;

    public TimeTaskDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        timeElapsedLabel.setText("Time Taken: " +  StringUtil.elapsedTimeToString(Stopwatch.getInstance(project.getName()).getElapsedTime()));
        projectLabel.setText("Task: " + repoHelper.getCurrentBranch());
    }

    private void onOK() {
        FileUtil.saveTaskInHistory(project, RepoHelper.getInstance().getCurrentTask(project), Stopwatch.getInstance(project.getName()).getElapsedTime());
        FileUtil.saveElapsedTime(project, 0L);
        NotificationUtil.showNotification("Finished logging for task - " + RepoHelper.getInstance().getCurrentBranch(), project);
        Stopwatch.getInstance(project.getName()).restartTimer();
        Stopwatch.getInstance(project.getName()).clearTimes();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(Project project) {
        TimeTaskDialog dialog = new TimeTaskDialog(project);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
