package common.requests;

public class Responce {
    private final boolean success;
    private final String answer;

    public Responce(boolean success, String answer){
        this.success = success;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isSuccess() {
        return success;
    }
}
