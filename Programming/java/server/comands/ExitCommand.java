package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ExitCommand extends CommandCollection {
    public ExitCommand(CollectionManager collectionManager){
        super(collectionManager, "exit", "завершить программу (без сохранения в файл)",
                new Argument[0]);
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
