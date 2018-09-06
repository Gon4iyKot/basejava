package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalFile());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File(".\\src\\com\\urise\\webapp");
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
        File homedir = new File(".");
        try {
            showFileTree(homedir);
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

    }

    private static void showFileTree(File homeDir) throws IOException {
        for (File anyFile : Objects.requireNonNull(homeDir.listFiles())) {
            if (anyFile.isDirectory()) {
                showFileTree(anyFile);
            } else {
                System.out.println("File:           " + anyFile.getName());
            }
        }
    }

}
