package com.wesleyelliott.timetracker.ui;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.wesleyelliott.timetracker.util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class HistoryDialog extends JDialog {
    private static final String[] columnNames = {"Date", "Task", "Time Taken"};

    private JPanel contentPane;
    private JButton buttonOK;
    private JTable historyTable;
    private Project project;

    public HistoryDialog(Project project) {
        this.project = project;

        DefaultTableModel model = new DefaultTableModel();
        historyTable.setModel(model);

        model.setColumnIdentifiers(columnNames);

        List<String> lines = FileUtil.getTaskHistory(project);
        lines = Lists.reverse(lines);
        for (String line : lines) {
            String[] data = line.split(",");
            model.addRow(data);
        }

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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


    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(Project project) {
        HistoryDialog dialog = new HistoryDialog(project);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}
