package com.cardio_generator.outputs;

/**
 *  The OutputStrategy interface represents a strategy for outputting patient data.
 *  Implementations of this interface define how patient data is outputted, such as to a console, file, WebSocket, or TCP socket.
 */
public interface OutputStrategy {
    /**
     * Outputs patient data with the specified patient ID, timestamp, label, and data.
     * @param patientId The ID of the patient associated with the data
     * @param timestamp The timestamp indicating when the data was generated
     * @param label The label or type of the data
     * @param data The actual data to be outputted
     */
    void output(int patientId, long timestamp, String label, String data);
}
