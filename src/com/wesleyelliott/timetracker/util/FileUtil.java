package com.wesleyelliott.timetracker.util;

import com.intellij.openapi.project.Project;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 2016/02/11.
 */
public class FileUtil {

    private static final String ELAPSED_TIME = "/.idea/timeTrackerCurrent.txt";
    private static final String TASKS_HISTORY = "/.idea/timeTrackerHistory.txt";

    public static void saveElapsedTime(Project myProject, Long elapsedTime) {
        Path fileP = Paths.get(myProject.getBasePath() + ELAPSED_TIME);
        try {
            if (!Files.exists(fileP)) {
                Files.createFile(fileP);
            }

            Files.write(fileP, Long.toString(elapsedTime).getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Long getElapsedTime(Project myProject) {

        Path fileP = Paths.get(myProject.getBasePath() + ELAPSED_TIME);

        try {
            if (!Files.exists(fileP)) {
                Files.createFile(fileP);
            }

            List<String> lines = Files.readAllLines(fileP);

            if (lines.size() != 0) {
                return Long.parseLong(lines.get(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    public static void saveTaskInHistory(Project myProject, String task, Long time) {
        Path fileP = Paths.get(myProject.getBasePath() + TASKS_HISTORY);
        String history = StringUtil.dateToString(System.currentTimeMillis()) + " - Task: " + task + " - Time: " + StringUtil.elapsedTimeToString(time) + "\n";
        try {
            if (!Files.exists(fileP)) {
                Files.createFile(fileP);
            }

            Files.write(fileP, history.getBytes("utf-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getTaskHistory(Project myProject) {

        Path fileP = Paths.get(myProject.getBasePath() + TASKS_HISTORY);

        try {
            if (!Files.exists(fileP)) {
                Files.createFile(fileP);
            }

            List<String> lines = Files.readAllLines(fileP);

            if (lines.size() != 0) {
                return lines;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
