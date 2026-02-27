package common.requests;

import common.Enums.Commands;

public class PrintFieldDescendingEyeColorRequest extends Request {
    public PrintFieldDescendingEyeColorRequest(){
        super(Commands.PRINT_FIELD_DESCENDING_EYE_COLOR);
    }
}
