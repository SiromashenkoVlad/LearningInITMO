package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ShuffleCommand extends CommandCollection {
    public ShuffleCommand(CollectionManager collectionManager){
        super(collectionManager, "shuffle", "перемешать элементы коллекции в случайном порядке",
                new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        try{
            this.getCollectionManager().shuffle();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды shuffle");
        }
    }
}
