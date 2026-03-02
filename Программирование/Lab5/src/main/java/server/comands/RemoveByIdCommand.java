package server.comands;

import common.Intefaces.OnesStringArgumentable;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class RemoveByIdCommand extends Command implements OnesStringArgumentable {
    CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager){
        super("remove_by_id", "удалит элемент из коллекции по его id", "{id}");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try {
            collectionManager.removeById(r.getId());
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды remove_by_id");
        }
    }
}
