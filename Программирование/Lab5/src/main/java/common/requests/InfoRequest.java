package common.requests;

import common.Enums.Commands;

public class InfoRequest extends Request{
    public InfoRequest(){
        super(Commands.INFO);
    }
}
