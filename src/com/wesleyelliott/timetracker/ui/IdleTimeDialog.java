package com.wesleyelliott.timetracker.ui;

import com.wesleyelliott.timetracker.TimeTracker;
import com.wesleyelliott.timetracker.util.OSxIdleTime;
import com.wesleyelliott.timetracker.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class IdleTimeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel idleTimeLabel;
    private DiscardIdleTimeListener listener;

    private long idleTime = 0L;

    public interface DiscardIdleTimeListener {
        void onDiscardTime(long idleTime);
        void onCancel();
    }

    public IdleTimeDialog() {
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

        new java.util.Timer().schedule(timerTask, 0, 1000);
    }

    private void onOK() {
        if (listener != null) {
            listener.onDiscardTime(idleTime);
        }
        dispose();
    }

    private void onCancel() {
        if (listener != null) {
            listener.onCancel();
        }
        dispose();
    }

    public void setConfirmListener(DiscardIdleTimeListener listener) {
        this.listener = listener;
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            long idleTime = OSxIdleTime.getIdleTimeMillis();
            if (idleTime > TimeTracker.IDLE_TIME) {
                IdleTimeDialog.this.idleTime = idleTime;
                updateUI();
            }
        }
    };

    private void updateUI() {
        EventQueue.invokeLater(new Runnable() {
           @Override
           public void run() {
               idleTimeLabel.setText("Oops! It looks like you've been idle for: " + StringUtil.elapsedTimeToString(IdleTimeDialog.this.idleTime));
           }
        });
    }

    public static void main(DiscardIdleTimeListener listener) {
        IdleTimeDialog dialog = new IdleTimeDialog();
        dialog.setConfirmListener(listener);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
