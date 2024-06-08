package data_management;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;
import com.alerts.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest3 {

    @Test
    void testAbnormalTemperature() {
        DataStorage dataStorage = new DataStorage();
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        Patient mockPatient = new Patient(1);
        mockPatient.addRecord(36.0, "Temperature", System.currentTimeMillis());
        mockPatient.addRecord(37.5, "Temperature", System.currentTimeMillis());

        alertGenerator.evaluateData(mockPatient);
        List<Alert> alerts = alertGenerator.getTriggeredAlerts();

        assertFalse(alerts.isEmpty()); // expecting no alerts
    }
}
