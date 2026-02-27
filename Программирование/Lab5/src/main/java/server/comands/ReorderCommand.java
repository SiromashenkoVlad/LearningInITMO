package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;

public class ReorderCommand extends Command{
    CollectionManager collectionManager;

    public ReorderCommand(CollectionManager collectionManager){
        super("reorder", "отсортировать коллекцию в порядке, обратном нынешнему", "");
        this.collectionManager = collectionManager;
    }

    public void execute(){
        collectionManager.reorder();
    }
}
