package common.requests;

import common.Enums.Commands;

public class ShowRequest extends Request{
    public ShowRequest(){
        super(Commands.SHOW);
    }
}
