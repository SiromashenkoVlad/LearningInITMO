package server.comands;

import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ShuffleCommand extends Command implements WithoutArguments {
    CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager){
        super("shuffle", "перемешать элементы коллекции в случайном порядке", "");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try{
            collectionManager.shuffle();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды shuffle");
        }
    }
}
