package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Represents a strategy for outputting data over TCP/IP.
 * This strategy establishes a TCP server to accept client connections and send data to them.
 * Each data transmission includes patient information such as ID, timestamp, label, and data
 */
public class TcpOutputStrategy implements OutputStrategy {

    /**The server socket for accepting client connections*/
    private ServerSocket serverSocket;
    /**The client socket for communicating with a connected client*/
    private Socket clientSocket;
    /**The PrintWriter for sending data to the connected client*/
    private PrintWriter out;

    /**
     * Constructs a TcpOutputStrategy with the specified port
     * @param port The port number on which the TCP server will listen for client connections
     */

    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs data to the connected client over TCP/IP
     * @param patientId The ID of the patient associated with the data
     * @param timestamp The timestamp indicating when the data was generated
     * @param label The label or type of the data
     * @param data The actual data to be outputted
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
