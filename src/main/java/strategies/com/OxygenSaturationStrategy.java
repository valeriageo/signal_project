package strategies.com;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {

    // Threshold for critical drop in oxygen saturation level
    private static final double MIN_OXYGEN_SATURATION = 90.0; // Minimum acceptable oxygen saturation level

    @Override
    public boolean checkAlert(Patient patient) {
        List<PatientRecord> readings = patient.getAllRecords();

        if (readings.isEmpty()) {
            return false;
        }

        // Check the latest oxygen saturation reading
        PatientRecord latestRecord = readings.get(readings.size() - 1);
        if ("Oxygen Saturation".equalsIgnoreCase(latestRecord.getRecordType())) {
            double oxygenSaturation = latestRecord.getMeasurementValue();
            // Check if oxygen saturation level is below the critical threshold
            if (oxygenSaturation < MIN_OXYGEN_SATURATION) {
                return true; // Alert if oxygen saturation level is critically low
            }
        }

        return false; // No alert
    }
}
