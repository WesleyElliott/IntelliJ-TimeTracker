package com.wesleyelliott.timetracker.ui;

import javax.swing.*;
import java.awt.event.*;

public class ConfirmDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private ConfirmListener listener;

    public interface ConfirmListener {
        void onConfirmed();
    }

    public ConfirmDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
    }

    private void onOK() {
        if (listener != null) {
            listener.onConfirmed();
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setConfirmListener(ConfirmListener listener) {
        this.listener = listener;
    }

    public static void main(ConfirmListener listener) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setConfirmListener(listener);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
