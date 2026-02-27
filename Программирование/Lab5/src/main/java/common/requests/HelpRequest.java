package common.requests;

import common.Enums.Commands;

public class HelpRequest extends Request{
    public HelpRequest(){
        super(Commands.HELP);
    }
}

