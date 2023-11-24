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
                    // System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    private static void findResources(File directory, List<File> foundResources, String dirName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(dirName) && file.toString().contains("src")) {
                    System.out.println("Found resources directory: " + file.getAbsolutePath());
                    System.out.println("Files in the resources directory:");
                    listFilesInDirectory(file, foundResources);
                } else if (file.isDirectory()) {
                    findResources(file, foundResources, dirName);  // Recursive call for subdirectories
                }
            }
        }
    }
}

//     public static void main(String[] args) {
//         try {
//             String projectPath = System.getProperty("user.dir");

//             File projectDirectory = new File(projectPath);

//             if (projectDirectory.exists() && projectDirectory.isDirectory()) {
//                 System.out.println("Searching for resources directory in the project:");
//                 List<File> resources = findResourcesDirectory(projectDirectory);
//                 System.out.println(resources.get(6));
//             } else {
//                 System.out.println("The project directory does not exist or is not a directory.");
//             }
//         } catch (Exception e) {
//             System.err.println("An error occurred: " + e.getMessage());
//         }
//     }
// }
