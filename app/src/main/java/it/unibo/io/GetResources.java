package it.unibo.io;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetResources {

    
    public static List<File> findResourcesDirectory(File directory, String dirName) {
        List<File> foundResources = new ArrayList<>();
        findResources(directory, foundResources, dirName);
        return foundResources;
    }

    private static void listFilesInDirectory(File directory, List<File> foundResources) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFilesInDirectory(file, foundResources);  // Recursive call for subdirectories
                } else {
                    foundResources.add(file);
                }
            }
        }
    }

    private static void findResources(File directory, List<File> foundResources, String dirName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(dirName) && file.toString().contains("src")) {
                    listFilesInDirectory(file, foundResources);
                } else if (file.isDirectory()) {
                    findResources(file, foundResources, dirName);
                }
            }
        }
    }
}