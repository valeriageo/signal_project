package com.alerts;

    public interface AlertI {
        String getPatientId();
        String getCondition();
        long getTimestamp();
        void trigger();
    }
