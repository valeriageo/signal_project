package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a strategy for outputting data to files.
 * This strategy writes data to text files in a specified directory.
 * Each label corresponds to a separate file
 */
public class FileOutputStrategy implements OutputStrategy { //changed the package name to adhere

    /**
     * The base directory where files will be stored
     */
    private String baseDirectory; //changing the variable to camelCase

    /**
     *A map to store the file paths corresponding to each label.
     *The key is the label, and the value is the file path
     */
    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Constructs a FileOutputStrategy with the specified base directory
     * @param baseDirectory The base directory where files will be stored
     */

    public FileOutputStrategy(String baseDirectory) { //changing the variable to camelCasegit branch


        this.baseDirectory = baseDirectory;


    }

    /**
     * Outputs data to a file
     * @param patientId The ID of the patient associated with the data
     * @param timestamp The timestamp indicating when the data was generated
     * @param label The label or type of the data
     * @param data The actual data to be outputted
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}