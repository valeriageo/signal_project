package com.alerts;
import com.alerts.AlertI;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityLevel;

    public PriorityAlertDecorator(AlertI decoratedAlert, String priorityLevel) {
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }

    @Override
    public void trigger() {
        System.out.println("Priority: " + priorityLevel);
        super.trigger();
    }
}
