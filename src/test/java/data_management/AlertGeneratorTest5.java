package data_management;

import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.Before;
import org.junit.Test;

import com.alerts.Alert;
import com.alerts.AlertGenerator;

import java.util.List;

import static org.junit.Assert.*;

public class AlertGeneratorTest5 {
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private Patient testPatient;
    private final int patientId = 1;

    @Before
    public void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
        testPatient = new Patient(patientId);
    }

    @Test
    public void testLowSaturationAlert() {
        dataStorage.addPatientData(patientId, 91, "OxygenSaturation", System.currentTimeMillis());
        // Ensure testPatient is updated
        testPatient.addRecord(91, "OxygenSaturation", System.currentTimeMillis());

        alertGenerator.evaluateData(testPatient);

        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();
        System.out.println(triggeredAlerts.toString());

        assertTrue("Low Saturation Alert was not triggered as expected",
                triggeredAlerts.stream().anyMatch(alert -> alert.getCondition().equals("Low Saturation")));
    }

    @Test
    public void testRapidDropAlert() {
        long currentTime = System.currentTimeMillis();
        dataStorage.addPatientData(patientId, 97, "OxygenSaturation", currentTime - 600000); // 10 minutes ago
        dataStorage.addPatientData(patientId, 90, "OxygenSaturation", currentTime);
        // Ensure testPatient is updated
        testPatient.addRecord(97, "OxygenSaturation", currentTime - 600000);
        testPatient.addRecord(90, "OxygenSaturation", currentTime);

        alertGenerator.evaluateData(testPatient);

        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();
        assertTrue("Rapid Drop Alert was not triggered as expected",
                triggeredAlerts.stream().anyMatch(alert -> alert.getCondition().equals("Rapid Drop")));
    }

    @Test
    public void testNoLowSaturationAlert() {
        dataStorage.addPatientData(patientId, 93, "OxygenSaturation", System.currentTimeMillis());
        // Ensure testPatient is updated
        testPatient.addRecord(93, "OxygenSaturation", System.currentTimeMillis());

        alertGenerator.evaluateData(testPatient);

        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();
        assertFalse("Low Saturation Alert was triggered unexpectedly",
                triggeredAlerts.stream().anyMatch(alert -> alert.getCondition().equals("Low Saturation")));
    }

    @Test
    public void testNoRapidDropAlert() {
        long currentTime = System.currentTimeMillis();
        dataStorage.addPatientData(patientId, 97, "OxygenSaturation", currentTime - 600000); // 10 minutes ago
        dataStorage.addPatientData(patientId, 95, "OxygenSaturation", currentTime);
        // Ensure testPatient is updated
        testPatient.addRecord(97, "OxygenSaturation", currentTime - 600000);
        testPatient.addRecord(95, "OxygenSaturation", currentTime);

        alertGenerator.evaluateData(testPatient);

        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();
        assertFalse("Rapid Drop Alert was triggered unexpectedly",
                triggeredAlerts.stream().anyMatch(alert -> alert.getCondition().equals("Rapid Drop")));
    }

    @Test
    public void testEdgeCaseLowSaturationAlert() {
        dataStorage.addPatientData(patientId, 92, "OxygenSaturation", System.currentTimeMillis());
        // Ensure testPatient is updated
        testPatient.addRecord(92, "OxygenSaturation", System.currentTimeMillis());

        alertGenerator.evaluateData(testPatient);

        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();
        assertFalse("Low Saturation Alert was triggered unexpectedly for threshold value",
                triggeredAlerts.stream().anyMatch(alert -> alert.getCondition().equals("Low Saturation")));
    }

    @Test
    public void testEdgeCaseRapidDropAlert() {
        long currentTime = System.currentTimeMillis();
        dataStorage.addPatientData(patientId, 97, "OxygenSaturation", currentTime - 600000); // 10 minutes ago
        dataStorage.addPatientData(patientId, 92, "OxygenSaturation", currentTime);
        // Ensure testPatient is updated
        testPatient.addRecord(97, "OxygenSaturation", currentTime - 600000);
        testPatient.addRecord(92, "OxygenSaturation", currentTime);

        alertGenerator.evaluateData(testPatient);

        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();
        assertTrue("Rapid Drop Alert was not triggered as expected for edge case",
                triggeredAlerts.stream().anyMatch(alert -> alert.getCondition().equals("Rapid Drop")));
    }
}
