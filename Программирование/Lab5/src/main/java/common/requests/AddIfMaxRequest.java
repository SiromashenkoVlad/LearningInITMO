package common.requests;

import common.Enums.Commands;
import common.Mainpart.Person;

public class AddIfMaxRequest extends Request {
    private final Person p;
    public AddIfMaxRequest(Person p){
        super(Commands.ADD_IF_MAX);
        this.p = p;
    }

    public Person getP() {
        return p;
    }
}
