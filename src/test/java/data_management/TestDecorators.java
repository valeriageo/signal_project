package data_management;

import com.alerts.Alert;
import com.alerts.AlertI;
import com.alerts.PriorityAlertDecorator;
import com.alerts.RepeatedAlertDecorator;

public class TestDecorators {
    // Create a basic alert
    AlertI alert = new Alert("1", "High Blood Pressure", System.currentTimeMillis());

    // Decorate the alert with priority and repetition
    AlertI priorityAlert = new PriorityAlertDecorator(alert, "High");

}

