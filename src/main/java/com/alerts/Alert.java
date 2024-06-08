package com.alerts;

public class Alert implements AlertI {
    private String patientId;
    private String condition;
    private long timestamp;

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void trigger() {
        System.out.println("Alert triggered: Patient ID = " + patientId +
                ", Condition = " + condition +
                ", Timestamp = " + timestamp);
    }
}
