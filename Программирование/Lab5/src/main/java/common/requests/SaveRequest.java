package common.requests;

import common.Enums.Commands;

public class SaveRequest extends Request {
    public SaveRequest(){
        super(Commands.SAVE);
    }
}
