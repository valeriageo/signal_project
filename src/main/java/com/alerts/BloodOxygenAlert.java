package com.alerts;

public class BloodOxygenAlert extends Alert {
    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String toString() {
        return "Blood Oxygen Alert: Patient ID = " + getPatientId() + ", Condition = " + getCondition() + ", Timestamp = " + getTimestamp();
    }
}

