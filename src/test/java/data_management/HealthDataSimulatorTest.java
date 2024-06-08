package data_management;

import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HealthDataSimulatorTest {

        @Test
        void testSingleton() {
            HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
            HealthDataSimulator instance2 = HealthDataSimulator.getInstance();
            assertSame(instance1, instance2);
        }
    }
