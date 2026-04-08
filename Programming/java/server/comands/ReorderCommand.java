package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ReorderCommand extends CommandCollection {
    public ReorderCommand(CollectionManager collectionManager){
        super(collectionManager, "reorder", "отсортировать коллекцию в порядке, обратном нынешнему",
                new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        try {
            this.getCollectionManager().reorder();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды reorder");
        }
    }
}
