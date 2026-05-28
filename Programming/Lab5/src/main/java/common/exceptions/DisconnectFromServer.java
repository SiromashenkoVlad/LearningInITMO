package common.exceptions;

import common.requests.Request;

public class DisconnectFromServer extends RuntimeException {
    private Request lastRequest;
    public DisconnectFromServer(String message, Request r) {
        super(message);
        lastRequest = r;
    }

    public DisconnectFromServer(String message) {
        super(message);
        lastRequest = null;
    }

    public Request getLastRequest() {
        return lastRequest;
    }
}
