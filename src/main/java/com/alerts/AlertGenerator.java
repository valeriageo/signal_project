package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;
import java.util.List;

public class AlertGenerator {
    private DataStorage dataStorage;
    private List<Alert> triggeredAlerts = new ArrayList<>();

    // Define factories for different types of alerts
    private AlertFactory bloodPressureAlertFactory = new BloodPressureAlertFactory();
    private AlertFactory bloodOxygenAlertFactory = new BloodOxygenAlertFactory();
    private AlertFactory ecgAlertFactory = new ECGAlertFactory();

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void evaluateData(Patient patient) {
        boolean trendAlert = isTrend(patient);
        boolean criticalThresholdAlert = isCritical(patient);
        boolean lowSaturationAlert = checkLowSaturation(patient);
        boolean rapidDropAlert = checkRapidDrop(patient);
        boolean abnormalTemperatureAlert = checkAbnormalTemperature(patient);

        if (trendAlert) {
            triggerAlert(bloodPressureAlertFactory.createAlert(Integer.toString(patient.getPatientId()), "Trend", System.currentTimeMillis()));
        }
        if (criticalThresholdAlert) {
            triggerAlert(bloodPressureAlertFactory.createAlert(Integer.toString(patient.getPatientId()), "Critical Threshold", System.currentTimeMillis()));
        }
        if (lowSaturationAlert) {
            triggerAlert(bloodOxygenAlertFactory.createAlert(Integer.toString(patient.getPatientId()), "Low Saturation", System.currentTimeMillis()));
        }
        if (rapidDropAlert) {
            triggerAlert(bloodOxygenAlertFactory.createAlert(Integer.toString(patient.getPatientId()), "Rapid Drop", System.currentTimeMillis()));
        }
        if (abnormalTemperatureAlert) {
            triggerAlert(ecgAlertFactory.createAlert(Integer.toString(patient.getPatientId()), "Abnormal Temperature", System.currentTimeMillis()));
        }
    }

    private void triggerAlert(Alert alert) {
        triggeredAlerts.add(alert);
        System.out.println(alert.toString());
    }

    // Existing methods for checking conditions
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

    private boolean checkLowSaturation(Patient patient) {
        for (PatientRecord record : patient.getAllRecords()) {
            if (record.getMeasurementValue() < 92) {
                return true;
            }
        }
        return false;
    }

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

    public List<Alert> getTriggeredAlerts() {
        return triggeredAlerts;
    }
}
