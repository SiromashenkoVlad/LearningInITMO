package common.requests;

import common.Enums.Commands;

public class ExitRequest extends Request{
    public ExitRequest(){
        super(Commands.EXIT);
    }
}
