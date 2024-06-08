package com.alerts;
    public class ECGAlert extends Alert {
        public ECGAlert(String patientId, String condition, long timestamp) {
            super(patientId, condition, timestamp);
        }

        @Override
        public String toString() {
            return "ECG Alert: Patient ID = " + getPatientId() + ", Condition = " + getCondition() + ", Timestamp = " + getTimestamp();
        }
    }

