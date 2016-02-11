package com.wesleyelliott.timetracker.util;

import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Wesley on 2016/02/11.
 */
public class FileUtil {

    private static final String SAVE_FILE = "/.idea/timeTracker.txt";

    public static void saveElapsedTime(Project myProject, Long elapsedTime) {
        try {
            File file = new File(myProject.getBasePath() + SAVE_FILE);

            if (!file.exists()) {
                file.createNewFile();
            }

            Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            try {
                out.write(Long.toString(elapsedTime));
            }
            finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Long getElapsedTime(Project myProject) {
        StringBuilder text = new StringBuilder();
        Scanner scanner;
        try {
            File file = new File(myProject.getBasePath() + SAVE_FILE);

            if (!file.exists()) {
                file.createNewFile();
            }

            scanner = new Scanner(new FileInputStream(myProject.getBasePath() + SAVE_FILE), "UTF-8");
            while (scanner.hasNextLine()){
                text.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(text.toString())) {
            return 0L;
        }
        return Long.parseLong(text.toString());
    }
}
