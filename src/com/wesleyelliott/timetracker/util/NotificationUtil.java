package com.wesleyelliott.timetracker.util;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

/**
 * Created by Wesley on 2016/02/10.
 */
public class NotificationUtil {

    public static final NotificationGroup GROUP_DISPLAY_ID_INFO = new NotificationGroup("TimeTrackerGroup", NotificationDisplayType.BALLOON, true);
    public static final NotificationGroup GROUP_DISPLAY_ID_INFO_LOG = new NotificationGroup("TimeTrackerGroup", NotificationDisplayType.NONE, true);
    public static final Logger LogUtil = Logger.getInstance("#com.intellij.notification.NotificationGroup");

    public static void showNotification(final String message) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification(message, NotificationType.INFORMATION);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Notifications.Bus.notify(notification, projects[0]);
            }
        });
    }

    public static void LogNotification(final String message) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO_LOG.createNotification(message, NotificationType.INFORMATION);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Notifications.Bus.notify(notification, projects[0]);
            }
        });
    }
}