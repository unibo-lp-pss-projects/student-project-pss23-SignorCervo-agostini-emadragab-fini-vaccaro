package it.unibo.io;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe GetResources fornisce metodi per trovare le risorse all'interno di una directory.
 */
public class GetResources {

     /**
     * Trova tutte le risorse all'interno di una directory specificata.
     *
     * @param directory la directory in cui cercare le risorse
     * @param dirName il nome della directory specifica da cercare
     * @return una lista di file che rappresentano le risorse trovate
     */
    public static List<File> findResourcesDirectory(File directory, String dirName) {
        List<File> foundResources = new ArrayList<>();
        findResources(directory, foundResources, dirName);
        return foundResources;
    }

    /**
     * Elenca tutti i file all'interno di una directory e delle sue sottodirectory.
     *
     * @param directory la directory da cui elencare i file
     * @param foundResources la lista in cui aggiungere i file trovati
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
     * Cerca ricorsivamente le risorse all'interno di una directory e delle sue sottodirectory.
     *
     * @param directory la directory in cui cercare le risorse
     * @param foundResources la lista in cui aggiungere le risorse trovate
     * @param dirName il nome della directory specifica da cercare
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