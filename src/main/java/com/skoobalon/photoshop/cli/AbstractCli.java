package com.skoobalon.photoshop.cli;

import java.io.File;
import java.util.Scanner;

public abstract class AbstractCli {

    protected static final String DEFAULT_OUTPUT_PATH = System.getProperty("user.home") + File.separator + "Pictures" + File.separator + "Adobe" + File.separator;
    protected static final String DEFAULT_PS_PATH = "C:\\Program Files\\Adobe\\Adobe Photoshop CC 2018\\";
    protected static final String RESOURCE_DIR = "Resources";
    protected static final String INDEX_FILE = "IconResources.idx";

    protected static String promptForPhotoshopPath() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Photoshop path is currently: " + DEFAULT_PS_PATH);
        System.out.println("If this is correct, press [Enter], otherwise type it in below");
        final String psPath = scanner.nextLine();
        return psPath.trim().length() == 0 ? DEFAULT_PS_PATH : psPath.trim();
    }

    protected static String promptForOutputPath() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Output path is currently: " + DEFAULT_OUTPUT_PATH);
        System.out.println("If this is correct, press [Enter], otherwise type it in below");
        final String outputPath = scanner.nextLine();
        return outputPath.trim().length() == 0 ? DEFAULT_OUTPUT_PATH : outputPath.trim();
    }

    protected static boolean promptForOverwrite() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("By default I will overwrite preexisting files. If this is alright, press [Enter],\n" +
                "otherwise type \"no\" below.");
        return scanner.nextLine().trim().length() == 0;
    }
}
