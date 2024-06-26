package com.alerts;

import com.data_management.Patient;
import strategies.com.AlertStrategy;

public class AlertContext {
    private AlertStrategy alertStrategy;

    public void setAlertStrategy(AlertStrategy alertStrategy) {
        this.alertStrategy = alertStrategy;
    }

    public boolean checkAlert(Patient patient) {
        return alertStrategy.checkAlert(patient);
    }
}

