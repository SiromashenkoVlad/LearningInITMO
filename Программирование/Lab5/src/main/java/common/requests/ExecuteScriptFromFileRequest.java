package common.requests;

import common.Enums.Commands;

public class ExecuteScriptFromFileRequest extends Request{
    private final String filename;
    public ExecuteScriptFromFileRequest(String filename){
        super(Commands.EXECUTE_SCRIPT);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
