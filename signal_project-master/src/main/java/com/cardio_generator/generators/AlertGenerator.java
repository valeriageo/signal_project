package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 *  Represents a generator for simulating alerts for patients.
 *  This generator triggers and resolves alerts based on random probabilities and sends them using an output strategy
 */

// changed class name to adhere to the Google java style guide
public class AlertGenerator implements PatientDataGenerator {

    // changed variable name to adhere to google java style guide
    private static final Random randomGenerator = new Random();
    private boolean[] alertStates; // changed the variable name to camelCase

    // Corrected constructor parameter name to adhere to camelCase convention

    /**
     * Constructs an AlertGenerator with the specified number of patients
     * @param patientCount The number of patients for whom alerts will be generated
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alerts for a specific patient and sends them using the provided output strategy
     * @param patientId The ID of the patient for which data is generated
     * @param outputStrategy The output strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Corrected comment format to adhere to google java style guide
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;

                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
