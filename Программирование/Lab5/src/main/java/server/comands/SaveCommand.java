package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;

public class SaveCommand extends Command{
    CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager){
        super("save", "сохранит коллекцию в файл", "");
        this.collectionManager = collectionManager;
    }

    public void execute(){
        collectionManager.save();
    }
}
