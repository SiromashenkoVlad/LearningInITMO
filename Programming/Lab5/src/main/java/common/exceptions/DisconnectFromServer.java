package common.exceptions;

public class DisconnectFromServer extends RuntimeException {
    public DisconnectFromServer(String message) {
        super(message);
    }
}
