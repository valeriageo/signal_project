package com.alerts;

import com.alerts.AlertI;

public abstract class AlertDecorator implements AlertI {
    protected AlertI decoratedAlert;

    public AlertDecorator(AlertI decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    @Override
    public void trigger() {
        decoratedAlert.trigger();
    }
}
