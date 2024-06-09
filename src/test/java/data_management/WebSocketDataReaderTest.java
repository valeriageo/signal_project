package data_management;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import com.data_management.WebSocketDataReader.MyWebSocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class WebSocketDataReaderTest {
    private WebSocketDataReader webSocketDataReader;
    private DataStorage dataStorage;
    private MyWebSocketClient myWebSocketClient;

    @BeforeEach
    public void setup() throws URISyntaxException {
        dataStorage = mock(DataStorage.class);
        webSocketDataReader = new WebSocketDataReader() {
            @Override
            public void readData(DataStorage dataStorage) throws IOException {

            }
        };
        myWebSocketClient = webSocketDataReader.new MyWebSocketClient(new URI("ws://localhost:8080"), dataStorage);
    }

    @Test
    public void testOnMessageValidJson() {
        String validMessage = "{\"patientId\":1,\"measurementValue\":98.6,\"recordType\":\"temperature\",\"timestamp\":1633024800}";
        myWebSocketClient.onMessage(validMessage);
        verify(dataStorage, times(1)).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    public void testOnMessageInvalidJson() {
        String invalidMessage = "invalid json";
        myWebSocketClient.onMessage(invalidMessage);
        verify(dataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
    }

    @Test
    public void testConnectAndReadDataSuccess() throws IOException {
        // Initialize WebSocketDataReader with proper constructor
        webSocketDataReader = new WebSocketDataReader() {
            @Override
            public void readData(DataStorage dataStorage) throws IOException {

            }
        };
        webSocketDataReader.connectAndReadData(dataStorage);
        assertNotNull(webSocketDataReader.client);
    }
}
