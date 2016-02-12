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
        writeFile(myProject, ELAPSED_TIME, Long.toString(elapsedTime));
    }

    public static Long getElapsedTime(Project myProject) {
        List<String> lines = readFile(myProject, ELAPSED_TIME);

        try {
            if (lines.size() > 0) {
                return Long.parseLong(lines.get(0));
            }
            return 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    public static void saveTaskInHistory(Project myProject, String task, Long time) {
        saveTaskInHistory(myProject, task, StringUtil.dateToString(System.currentTimeMillis()), StringUtil.elapsedTimeToString(time));
    }

    public static void saveTaskInHistory(Project myProject, String task, String date, String time) {
        String history = date + "," + task + "," + time + "\n";
        writeFile(myProject, TASKS_HISTORY, history, true);
    }
    public static List<String> getTaskHistory(Project myProject) {
        return readFile(myProject, TASKS_HISTORY);
    }

    public static void clearHistory(Project myProject) {
        clearFile(myProject, TASKS_HISTORY);
    }

    private static List<String> readFile(Project myProject, String path) {
        Path fileP = Paths.get(myProject.getBasePath() + path);

        try {
            if (!Files.exists(fileP)) {
                Files.createFile(fileP);
            }

            List<String> lines = Files.readAllLines(fileP);

            if (lines.size() != 0) {
                return lines;
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }

        return new ArrayList<>();
    }

    private static void writeFile(Project myProject, String path, String value) {
        writeFile(myProject, path, value, false);
    }

    private static void writeFile(Project myProject, String path, String value, boolean append) {
        Path fileP = Paths.get(myProject.getBasePath() + path);
        try {
            if (!Files.exists(fileP)) {
                Files.createFile(fileP);
            }

            if (append) {
                Files.write(fileP, value.getBytes("utf-8"), StandardOpenOption.APPEND);
            } else {
                Files.write(fileP, value.getBytes("utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearFile(Project mProject, String path) {
        writeFile(mProject, path, "");
    }

}
