package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;

public class ClearCommand extends Command{
    CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager){
        super("clear", "очистит коллекцию", "");
        this.collectionManager = collectionManager;
    }

    public void execute(){
        collectionManager.clear();
    }
}
