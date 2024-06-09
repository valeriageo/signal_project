package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Represents a data generator for simulating blood saturation levels of patients.
 * This generator creates pseudo-random blood saturation values for each patient and sends them using an output strategy
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {

    /**A random number generator for generating blood saturation values*/
    private static final Random random = new Random();
    /**An array to store the last saturation values for each patient*/
    private int[] lastSaturationValues;

    /**
     * Constructs a BloodSaturationDataGenerator with the specified number of patients
     * @param patientCount The number of patients for whom blood saturation data will be generated
     */

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates blood saturation data for a specific patient and sends it using the provided output strategy
     * @param patientId The ID of the patient for which data is generated
     * @param outputStrategy The output strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
