package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;

public class ExecuteScriptCommand extends Command{
    public ExecuteScriptCommand(){
        super("execute_script", "считать и исполнить скрипт" +
                " из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в " +
                "интерактивном режиме", new Argument[]{
                new Argument("{filename}", String.class)});
    }

    @Override
    public Responce execute(Request r) {
        return new Responce(true, "");
    }
}
