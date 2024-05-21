package data_management;

import java.util.List;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertGeneratorTest {

    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    void testIncreasingTrend() {
        Patient patient = new Patient(1);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(110, "BloodPressure", currentTime);
        patient.addRecord(121, "BloodPressure", currentTime + 1000);
        patient.addRecord(132, "BloodPressure", currentTime + 2000);

        alertGenerator.evaluateData(patient);
        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();

        assertEquals(2, triggeredAlerts.size());
        assertEquals("Trend", triggeredAlerts.get(0).getCondition());
    }

    @Test
    void testDecreasingTrend() {
        Patient patient = new Patient(2);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(150, "BloodPressure", currentTime);
        patient.addRecord(139, "BloodPressure", currentTime + 1000);
        patient.addRecord(128, "BloodPressure", currentTime + 2000);

        alertGenerator.evaluateData(patient);
        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();

        assertEquals(2, triggeredAlerts.size());
        assertEquals("Trend", triggeredAlerts.get(0).getCondition());
    }

    @Test
    void testCriticalThresholds() {
        Patient patient = new Patient(3);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(190, "BloodPressure", currentTime);
        patient.addRecord(80, "BloodPressure", currentTime + 1000);

        alertGenerator.evaluateData(patient);
        List<Alert> triggeredAlerts = alertGenerator.getTriggeredAlerts();

        assertEquals(1, triggeredAlerts.size());
        assertEquals("Critical Threshold", triggeredAlerts.get(0).getCondition());
    }
}
