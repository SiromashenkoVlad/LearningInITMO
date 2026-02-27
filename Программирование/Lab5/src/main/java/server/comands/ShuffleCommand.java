package server.comands;

import server.managers.CollectionManager;

public class ShuffleCommand extends Command{
    CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager){
        super("shuffle", "перемешать элементы коллекции в случайном порядке", "");
        this.collectionManager = collectionManager;
    }

    public void execute(){
        collectionManager.shuffle();
    }
}
