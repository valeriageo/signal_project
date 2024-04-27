package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The PatientDataGenerator interface represents a data generator for simulating patient data.
 * Implementations of this interface should generate data for a specific patient and use the provided output strategy
 * to output the generated data.
 */
public interface PatientDataGenerator {
    /**
     * Generates patient data and outputs it using the specified output strategy
     * @param patientId The ID of the patient for which data is generated
     * @param outputStrategy The output strategy used to output the generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
