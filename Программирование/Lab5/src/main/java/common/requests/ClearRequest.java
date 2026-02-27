package common.requests;

import common.Enums.Commands;

public class ClearRequest extends Request{
    public ClearRequest(){
        super(Commands.CLEAR);
    }
}
