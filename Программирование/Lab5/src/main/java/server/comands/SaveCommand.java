package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class SaveCommand extends CommandCollection {
    public SaveCommand(CollectionManager collectionManager){
        super(collectionManager, "save", "сохранит коллекцию в файл", new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        try {
            this.getCollectionManager().save();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды save");
        }
    }
}
