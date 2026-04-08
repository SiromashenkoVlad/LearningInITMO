package common.requests;

import java.io.Serializable;

public class Responce implements Serializable {
    private static final long serialVersionUID = 1L;

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
