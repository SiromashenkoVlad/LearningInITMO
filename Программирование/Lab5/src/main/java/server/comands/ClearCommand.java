package server.comands;

import common.Enums.Commands;
import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

import java.lang.reflect.WildcardType;

public class ClearCommand extends Command implements WithoutArguments {
    CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager){
        super("clear", "очистит коллекцию", "");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try{
            collectionManager.clear();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды clear");
        }
    }
}
