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

    public static void showNotification(final String message, final Project project) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification(message, NotificationType.INFORMATION);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                for (Project openProject : projects) {
                    if (project.getName().equalsIgnoreCase(openProject.getName())) {
                        Notifications.Bus.notify(notification, project);
                        break;
                    }
                }

            }
        });
    }

    public static void LogNotification(final String message, final Project project) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO_LOG.createNotification(message, NotificationType.INFORMATION);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                for (Project openProject : projects) {
                    if (project.getName().equalsIgnoreCase(openProject.getName())) {
                        Notifications.Bus.notify(notification, project);
                        break;
                    }
                }
            }
        });
    }
}