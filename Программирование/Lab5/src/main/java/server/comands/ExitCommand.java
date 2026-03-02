package server.comands;

import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;

public class ExitCommand extends Command implements WithoutArguments {
    public ExitCommand(){
        super("exit", "завершить программу (без сохранения в файл)", "");
    }

    @Override
    public Responce execute(Request r){
        try{
            return new Responce(true, "Свобода");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды exit");
        }
    }
}
