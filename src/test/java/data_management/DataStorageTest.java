package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataReader;
import com.data_management.MockDataReader;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords()
    {
        // TODO Perhaps you can implement a mock data reader to mock the test data?
        DataReader reader = new MockDataReader();
        DataStorage storage = new DataStorage();
        try
        {
            reader.readData(storage);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        //DONE
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }
        @Test
        void testSingleton() {
            DataStorage instance1 = DataStorage.getInstance();
            DataStorage instance2 = DataStorage.getInstance();
            assertSame(instance1, instance2);
        }

        @Test
        void testAddAndGetPatientData() {
            DataStorage dataStorage = DataStorage.getInstance();
            dataStorage.addPatientData(1, 120, "BloodPressure", System.currentTimeMillis());
            assertFalse(dataStorage.getRecords(1, 0, System.currentTimeMillis()).isEmpty());
        }
    }


