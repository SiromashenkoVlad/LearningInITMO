package common.requests;

import common.Enums.Commands;
import common.Mainpart.Person;

public class UpdateIdRequest extends Request{
    private final int id;
    private final Person p;
    public UpdateIdRequest(int id, Person p){
        super(Commands.UPDATE_ID);
        this.id = id;
        this.p = p;
    }

    public int getId() {
        return id;
    }

    public Person getP() {
        return p;
    }
}
