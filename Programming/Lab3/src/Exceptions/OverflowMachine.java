package Exceptions;

public class OverflowMachine extends RuntimeException {
    public OverflowMachine(String message, Integer currentCash) {
        super(message + currentCash);
    }

    @Override
    public String getMessage() {
        return "Ошибка добавления нового пассажира";
    }
}
