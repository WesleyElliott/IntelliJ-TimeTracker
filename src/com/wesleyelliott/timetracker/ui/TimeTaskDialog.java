package com.wesleyelliott.timetracker.ui;

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
    private Stopwatch stopwatch = Stopwatch.getInstance();
    private RepoHelper repoHelper = RepoHelper.getInstance();

    public TimeTaskDialog() {
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

        timeElapsedLabel.setText("Time Taken: " +  StringUtil.elapsedTimeToString(stopwatch.getElapsedTime()));
        projectLabel.setText("Project: " + repoHelper.getCurrentBranch());
    }

    private void onOK() {
        stopwatch.restartTimer();
        NotificationUtil.showNotification("Finished logging for project - " + RepoHelper.getInstance().getCurrentBranch());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        TimeTaskDialog dialog = new TimeTaskDialog();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
