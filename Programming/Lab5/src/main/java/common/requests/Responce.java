package common.requests;

import common.Mainpart.Person;

import java.io.Serializable;
import java.util.List;

public class Responce implements Serializable {
    private static final long serialVersionUID = 2L;

    private final boolean success;
    private final String answer;
    List<Person> collection;

    public Responce(boolean success, String answer){
        this.success = success;
        this.answer = answer;
        collection = null;
    }

    public Responce(boolean success, String answer, List<Person> collection){
        this.success = success;
        this.answer = answer;
        this.collection = collection;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Person> getCollection(){
        return collection;
    }
}
