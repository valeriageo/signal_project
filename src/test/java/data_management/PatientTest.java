package data_management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.data_management.*;

import java.util.List;

class PatientTest {

    @Test
    void testAddAndGetRecords() {
        Patient patient = new Patient(1);

        // Adding records
        patient.addRecord(75.0, "HeartRate", 1625097600000L); // Timestamp for July 1, 2021
        patient.addRecord(120.0, "BloodPressure", 1625184000000L); // Timestamp for July 2, 2021
        patient.addRecord(85.0, "HeartRate", 1625270400000L); // Timestamp for July 3, 2021

        // Retrieving records within a specific time range
        List<PatientRecord> records = patient.getRecords(1625097600000L, 1625184000000L);
        assertEquals(2, records.size()); // Two records should be retrieved

        // Validating the contents of the retrieved records
        assertEquals(75.0, records.get(0).getMeasurementValue());
        assertEquals("HeartRate", records.get(0).getRecordType());
        assertEquals(1625097600000L, records.get(0).getTimestamp());

        assertEquals(120.0, records.get(1).getMeasurementValue());
        assertEquals("BloodPressure", records.get(1).getRecordType());
        assertEquals(1625184000000L, records.get(1).getTimestamp());
    } 
}
