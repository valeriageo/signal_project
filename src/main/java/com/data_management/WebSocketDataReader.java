package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class WebSocketDataReader implements DataReader {
    public MyWebSocketClient client;

    @Override
    public void connectAndReadData(DataStorage dataStorage) throws IOException {
        try {
            client = new MyWebSocketClient(new URI("ws://localhost:8080"), dataStorage);
            client.connect();
        } catch (URISyntaxException e) {
            throw new IOException("Invalid WebSocket URI", e);
        }
    }

    public class MyWebSocketClient extends WebSocketClient {
        private final DataStorage dataStorage;

        public MyWebSocketClient(URI serverUri, DataStorage dataStorage) {
            super(serverUri);
            this.dataStorage = dataStorage;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println("Connected to server");
        }

        @Override
        public void onMessage(String message) {
            System.out.println("Received message: " + message);
            try {
                JSONObject jsonObject = new JSONObject(message);
                int patientId = jsonObject.getInt("patientId");
                double measurementValue = jsonObject.getDouble("measurementValue");
                String recordType = jsonObject.getString("recordType");
                long timestamp = jsonObject.getLong("timestamp");

                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("Failed to parse message: " + message);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            System.out.println("Connection closed: " + reason);
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
        }
    }
}
