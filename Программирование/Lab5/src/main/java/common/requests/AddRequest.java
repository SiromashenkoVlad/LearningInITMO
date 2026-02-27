package common.requests;

import common.Enums.Commands;
import common.Mainpart.Person;

public class AddRequest extends Request{
    private final Person person;
    public AddRequest(Person p){
        super(Commands.ADD);
        person = p;
    }

    @Override
    public Commands getName() {
        return super.getName();
    }
}
