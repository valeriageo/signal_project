package strategies.com;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public class BloodPressureStrategy implements AlertStrategy {
    private static final int HIGH_BP = 130;
    private static final int LOW_BP = 90;
    private static final int TREND_COUNT = 3;

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> readings = patient.getAllRecords();

        if (readings.isEmpty()) {
            return false;
        }

        // Check for high or low blood pressure
        PatientRecord lastRecord = readings.get(readings.size() - 1);
        if ("Blood Pressure".equalsIgnoreCase(lastRecord.getRecordType())) {
            double lastBP = lastRecord.getBloodPressure();
            if (lastBP >= HIGH_BP || lastBP <= LOW_BP) {
                return true;
            }
        }

        // Check for increasing trend
        int count = 0;
        for (int i = readings.size() - 1; i > 0; i--) {
            PatientRecord current = readings.get(i);
            PatientRecord previous = readings.get(i - 1);
            if ("Blood Pressure".equalsIgnoreCase(current.getRecordType()) &&
                    "Blood Pressure".equalsIgnoreCase(previous.getRecordType())) {
                if (current.getBloodPressure() > previous.getBloodPressure()) {
                    count++;
                } else {
                    break;
                }
                if (count == TREND_COUNT) {
                    return true;
                }
            }
        }

        // Check for decreasing trend
        count = 0;
        for (int i = readings.size() - 1; i > 0; i--) {
            PatientRecord current = readings.get(i);
            PatientRecord previous = readings.get(i - 1);
            if ("Blood Pressure".equalsIgnoreCase(current.getRecordType()) &&
                    "Blood Pressure".equalsIgnoreCase(previous.getRecordType())) {
                if (current.getBloodPressure() < previous.getBloodPressure()) {
                    count++;
                } else {
                    break;
                }
                if (count == TREND_COUNT) {
                    return true;
                }
            }
        }

        return false;
    }
}
