package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    private static String space = "";

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("----------------------------");
        File path = new File(".");
        printFileNames(path);
    }

    private static void printFileNames(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(space + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(space + "Directory: " + file.getName());
                    space += "  ";
                    printFileNames(file);
                }
            }
            if (space.length() != 0) {
                space = space.substring(0, space.length() - 2);
            }
        }
    }
}