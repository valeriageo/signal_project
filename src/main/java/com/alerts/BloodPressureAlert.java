package com.alerts;

public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String toString() {
        return "Blood Pressure Alert: Patient ID = " + getPatientId() + ", Condition = " + getCondition() + ", Timestamp = " + getTimestamp();
    }
}
