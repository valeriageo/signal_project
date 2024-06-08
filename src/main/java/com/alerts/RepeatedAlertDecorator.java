package com.alerts;
import com.alerts.AlertI;

public abstract class RepeatedAlertDecorator implements AlertI {
    private int repeatCount;

    public RepeatedAlertDecorator(AlertI decoratedAlert, int repeatCount) {
        super();
        this.repeatCount = repeatCount;
    }

    @Override
    public void trigger() {
        for (int i = 0; i < repeatCount; i++) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000); // Pause for a second between repeats
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

