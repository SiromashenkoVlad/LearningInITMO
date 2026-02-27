package common.requests;

import common.Enums.Commands;

public class ReorderRequest extends Request {
    public ReorderRequest(){
        super(Commands.REORDER);
    }
}
