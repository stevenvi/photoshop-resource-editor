package com.skoobalon.photoshop.cli;

import java.io.File;
import java.util.Scanner;

public abstract class AbstractCli {

    protected static final String DEFAULT_EXTRACTED_DATA_PATH = System.getProperty("user.home") + File.separator + "Pictures" + File.separator + "Adobe" + File.separator;
    protected static final String DEFAULT_PS_PATH = "C:\\Program Files\\Adobe\\Adobe Photoshop CC 2018\\";
    protected static final String RESOURCE_DIR = "Resources";
    protected static final String INDEX_FILE = "IconResources.idx";

    protected static String promptForPhotoshopPath() {
        return prompt("Photoshop path", DEFAULT_PS_PATH);
    }

    protected static String promptForOutputPath() {
        return prompt("Output path", DEFAULT_EXTRACTED_DATA_PATH);
    }

    protected static String prompt(String prompt, String defaultValue) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(prompt + " is currently: " + defaultValue);
        System.out.println("If this is correct, press [Enter], otherwise type it in below");
        final String line = scanner.nextLine();
        return line.trim().length() == 0 ? defaultValue : line.trim();
    }

    protected static boolean promptForOverwrite() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("By default I will overwrite preexisting files. If this is alright, press [Enter],\n" +
                "otherwise type \"no\" below.");
        return scanner.nextLine().trim().length() == 0;
    }

    protected static void createPath(File path) {
        if (!path.exists()) {
            System.out.print("Creating " + path.getAbsolutePath() + "...");
            System.out.println(path.mkdirs() ? " done" : " failed");
        }
    }
}
