package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;

public class RemoveByIdCommand extends Command{
    CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager){
        super("remove_by_id", "удалит элемент из коллекции по его id", "{id}");
        this.collectionManager = collectionManager;
    }

    public void execute(int id){
        collectionManager.removeById(id);
    }
}
