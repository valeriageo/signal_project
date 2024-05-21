package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    private List<Alert> triggeredAlerts = new ArrayList<>();

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        boolean trendAlert = isTrend(patient);
        boolean criticalThresholdAlert = isCritical(patient);

        if (trendAlert) {
            triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Trend", System.currentTimeMillis()));
        }

        if (criticalThresholdAlert) {
            triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Critical Threshold", System.currentTimeMillis()));
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        triggeredAlerts.add(alert);
        System.out.print("Alert triggered: Patient ID = " + alert.getPatientId() +
                "Condition = " + alert.getCondition() +
                "Timestamp = " + alert.getTimestamp());
    }

    private boolean isTrend(Patient patient) {
        int sequenceCount = 0;
        double lastReading = 0;
        for (PatientRecord record : patient.getAllRecords()) {
            double measurement = record.getMeasurementValue();
            if (measurement >= lastReading + 10 || measurement <= lastReading - 10) {
                sequenceCount++;
            } else {
                sequenceCount = 0;
            }
            lastReading = measurement;

            if (sequenceCount >= 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isCritical(Patient patient) {
        for (PatientRecord record : patient.getAllRecords()) {
            double systolic = record.getMeasurementValue();
            double diastolic = record.getMeasurementValue();
            if (systolic > 180 || systolic < 90 || diastolic > 120 || diastolic < 60) {
                return true;
            }
        }
        return false;
    }

    public List<Alert> getTriggeredAlerts() {
        return triggeredAlerts;
    }

    private boolean checkLowSaturation(Patient patient) {
        for (PatientRecord record : patient.getAllRecords()) {
            if (record.getMeasurementValue() < 92) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the blood oxygen saturation level drops by 5% or more within a 10-minute interval.
     *
     * @param patient the patient data to evaluate
     * @return true if there is a rapid drop, false otherwise
     */
    private boolean checkRapidDrop(Patient patient) {
        List<PatientRecord> records = patient.getAllRecords();
        for (int i = 1; i < records.size(); i++) {
            PatientRecord previousRecord = records.get(i - 1);
            PatientRecord currentRecord = records.get(i);

            double previousSaturation = previousRecord.getMeasurementValue();
            double currentSaturation = currentRecord.getMeasurementValue();
            long timeDifference = currentRecord.getTimestamp() - previousRecord.getTimestamp();
            if (timeDifference <= 10 * 60 * 1000 && (previousSaturation - currentSaturation) >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAbnormalTemperature(Patient patient) {
        for (PatientRecord record : patient.getAllRecords()) {
            double temperature = record.getMeasurementValue();
            if (temperature > 38 || temperature < 35) {
                return true;
            }
        }
        return false;
    }
    private double calculateAverage(List<PatientRecord> records) {
        return records.stream()
                .mapToDouble(PatientRecord::getMeasurementValue)
                .average()
                .orElse(Double.NaN);
    }
    private boolean isPeakAbnormal(double value, double average) {
        // Define the threshold for what is considered a "peak"
        double threshold = average * 1.2; // Example threshold: 20% above the average
        return value > threshold;
    }
}