package common.requests;

import common.Enums.Commands;

public class RemoveByIdRequest extends Request{
    private final int id;
    public RemoveByIdRequest(int id){
        super(Commands.UPDATE_ID);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
