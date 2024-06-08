package data_management;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.alerts.Alert;
import com.alerts.AlertGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest4 {

    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private Patient mockPatient;
    private long startTime;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
        mockPatient = new Patient(1);
        startTime = System.currentTimeMillis();
    }

    @Test
    void testGetRecordsMethod() {
        mockPatient.addRecord(new PatientRecord(1, 36.0, "Temperature", startTime - 10000)); // 10 seconds before
        mockPatient.addRecord(new PatientRecord(1, 37.5, "Temperature", startTime)); // Current time
        mockPatient.addRecord(new PatientRecord(1, 38.5, "Temperature", startTime + 10000)); // 10 seconds after

        List<PatientRecord> records = mockPatient.getRecords(startTime - 20000, startTime + 10000);

        assertEquals(3, records.size()); // Expect 2 records within the time range
        assertTrue(records.stream().anyMatch(record -> record.getMeasurementValue() == 36.0));
        assertTrue(records.stream().anyMatch(record -> record.getMeasurementValue() == 37.5));
    }
}
