package client.session;

import client.connection.ConnectionManager;
import common.requests.Argument;

import java.io.IOException;
import java.util.Map;

public class SessionInitializer {
    public Map<String, Argument[]> initialize(ConnectionManager connectionManager)
            throws IOException, ClassNotFoundException {
        return receiveUsages(connectionManager);
    }

    private Map<String, Argument[]> receiveUsages(ConnectionManager connectionManager)
            throws IOException, ClassNotFoundException {
        return (Map<String, Argument[]>) connectionManager.gettingResponse();
    }
}
