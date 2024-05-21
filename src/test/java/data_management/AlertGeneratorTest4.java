package data_management;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest4 {

    @Test
    void testGetRecordsMethod() {
        DataStorage dataStorage = new DataStorage();
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        Patient mockPatient = new Patient(1);

        long startTime = System.currentTimeMillis();
        mockPatient.addRecord(new PatientRecord(1, 36.0, "Temperature", startTime - 10000)); // 10 seconds before
        mockPatient.addRecord(new PatientRecord(1, 37.5, "Temperature", startTime)); // Current time
        mockPatient.addRecord(new PatientRecord(1, 38.5, "Temperature", startTime + 10000)); // 10 seconds after

        List<PatientRecord> records = mockPatient.getRecords(startTime - 20000, startTime);

        assertEquals(2, records.size()); // Expect 2 records within the last 20 seconds
        assertTrue(records.stream().anyMatch(record -> record.getMeasurementValue() == 36.0));
        assertTrue(records.stream().anyMatch(record -> record.getMeasurementValue() == 37.5));
        assertFalse(records.stream().anyMatch(record -> record.getMeasurementValue() == 38.5)); // This record is outside the time frame
    }
}
