package com.wesleyelliott.timetracker.ui;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.wesleyelliott.timetracker.util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class HistoryDialog extends JDialog implements ConfirmDialog.ConfirmListener {
    private static final String[] columnNames = {"Date", "Task", "Time Taken"};

    private JPanel contentPane;
    private JButton buttonOK;
    private JTable historyTable;
    private JButton clearHistory;
    private JButton saveChangesButton;
    private Project project;
    private TimeTrackerTableModel model;

    public HistoryDialog(Project project) {
        this.project = project;

        model = new TimeTrackerTableModel(this.project);
        historyTable.setModel(model);

        model.setColumnIdentifiers(columnNames);

        updateDataModel();

        setContentPane(contentPane);
        setModal(true);
        setTitle("Time Tracker History");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        clearHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ConfirmDialog.main(HistoryDialog.this);
            }
        });

        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSaveChanges();
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

    private void updateDataModel() {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        List<String> lines = FileUtil.getTaskHistory(project);
        lines = Lists.reverse(lines);
        for (String line : lines) {
            String[] data = line.split(",");
            model.addRow(data);
        }
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void onClearHistory() {
        FileUtil.clearHistory(project);
        updateDataModel();
    }

    private void onSaveChanges() {
        model.fireTableDataChanged();
    }

    @Override
    public void onConfirmed() {
        onClearHistory();
    }

    public static void main(Project project) {
        HistoryDialog dialog = new HistoryDialog(project);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}
