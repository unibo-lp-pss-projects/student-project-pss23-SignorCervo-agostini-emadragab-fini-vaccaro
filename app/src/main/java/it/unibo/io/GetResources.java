package it.unibo.io;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * GetResources is a utility class for finding resources within a specified directory.
 */

public class GetResources {

    /**
     * Find all files within the specified directory with the given name.
     *
     * @param directory The directory to start searching for resources.
     * @param dirName   The name of the directory to find.
     * @return A list of files found within the specified directory.
     */
    
    public static List<File> findResourcesDirectory(File directory, String dirName) {
        List<File> foundResources = new ArrayList<>();
        findResources(directory, foundResources, dirName);
        return foundResources;
    }

    /**
     * Recursively list all files in a directory and its subdirectories.
     *
     * @param directory      The current directory to list files from.
     * @param foundResources The list to store found files.
     */

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

    /**
     * Find resources within the specified directory with the given name.
     *
     * @param directory      The current directory to search for resources.
     * @param foundResources The list to store found resources.
     * @param dirName        The name of the directory to find.
     */

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