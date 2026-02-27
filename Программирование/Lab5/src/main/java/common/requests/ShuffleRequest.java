package common.requests;

import common.Enums.Commands;

public class ShuffleRequest extends Request{
    public ShuffleRequest(){
        super(Commands.SHUFFLE);
    }
}
