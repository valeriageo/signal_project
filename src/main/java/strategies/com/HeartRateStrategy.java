package strategies.com;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public class HeartRateStrategy implements AlertStrategy {

    // Thresholds for abnormal heart rates
    private static final int MIN_HEART_RATE = 60; // Minimum normal heart rate
    private static final int MAX_HEART_RATE = 100; // Maximum normal heart rate

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> readings = patient.getAllRecords();

        if (readings.isEmpty()) {
            return false;
        }

        // Check the latest heart rate reading
        PatientRecord latestRecord = readings.get(readings.size() - 1);
        if ("Heart Rate".equalsIgnoreCase(latestRecord.getRecordType())) {
            int heartRate = (int) latestRecord.getMeasurementValue();
            // Check if heart rate is outside the normal range
            if (heartRate < MIN_HEART_RATE || heartRate > MAX_HEART_RATE) {
                return true; // Alert if heart rate is abnormal
            }
        }

        return false; // No alert
    }
}
