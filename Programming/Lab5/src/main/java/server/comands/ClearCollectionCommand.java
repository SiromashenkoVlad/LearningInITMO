package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

import java.sql.SQLException;

public class ClearCollectionCommand extends CollectionCommand {
    public ClearCollectionCommand(CollectionManager collectionManager){
        super(collectionManager, "clear", "очистит коллекцию", new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        try{
            this.getCollectionManager().clear(r.getCredentialsProvider().getLogin());
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды clear");
        }
    }
}
