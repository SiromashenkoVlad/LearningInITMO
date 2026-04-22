package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ExitCommand extends CommandCollection {
    private final SaveCommand saveCommand = new SaveCommand(this.getCollectionManager());
    public ExitCommand(CollectionManager collectionManager){
        super(collectionManager, "exit", "завершить программу (без сохранения в файл)",
                new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        try{
            saveCommand.execute(r);
            return new Responce(true, "Свобода");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды exit");
        }
    }
}
