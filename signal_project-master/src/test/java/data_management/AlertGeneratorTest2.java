package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

class AlertGeneratorTest2 {
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private Patient testPatient;
    private final int patientId = 1;

    @Before
    public void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
        testPatient = new Patient(patientId);
        // Populate the DataStorage with some test data for the patient
        dataStorage.addPatientData(patientId, 95, "OxygenSaturation", System.currentTimeMillis());
        // Add more records as needed to test the functionality
    }

    @Test
    public void testLowSaturationAlert() {
        // Add a record that should trigger the low saturation alert
        dataStorage.addPatientData(patientId, 91, "OxygenSaturation", System.currentTimeMillis());
        alertGenerator.evaluateData(testPatient);
        assertTrue("Low Saturation Alert was not triggered as expected",
                alertGenerator.getTriggeredAlerts().stream()
                        .anyMatch(alert -> alert.getCondition().equals("Low Saturation")));
    }

    @Test
    public void testRapidDropAlert() {
        // Add records to simulate a rapid drop in oxygen saturation
        long currentTime = System.currentTimeMillis();
        dataStorage.addPatientData(patientId, 97, "OxygenSaturation", currentTime - 600000); // 10 minutes ago
        dataStorage.addPatientData(patientId, 90, "OxygenSaturation", currentTime);
        alertGenerator.evaluateData(testPatient);
        assertTrue("Rapid Drop Alert was not triggered as expected",
                alertGenerator.getTriggeredAlerts().stream()
                        .anyMatch(alert -> alert.getCondition().equals("Rapid Drop")));
    }
}
