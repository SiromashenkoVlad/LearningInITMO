package Exceptions;

public class GetAwayFromPersecution extends RuntimeException {
    public GetAwayFromPersecution(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Бандиты ушли от преследования";
    }
}
