package client.network;

import client.connection.ConnectionManager;
import common.requests.Request;
import common.requests.Responce;

import java.io.IOException;

public class BaseRequestSender implements RequestSender {
    private final ConnectionManager connectionManager;

    public BaseRequestSender(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Responce send(Request request) throws IOException, ClassNotFoundException {
        connectionManager.sendingRequest(request);
        return (Responce) connectionManager.gettingResponse();
    }
}
