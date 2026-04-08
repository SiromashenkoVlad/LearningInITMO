package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class RemoveByIdCommand extends CommandCollection {
    public RemoveByIdCommand(CollectionManager collectionManager){
        super(collectionManager, "remove_by_id", "удалит элемент из коллекции по его id",
                new Argument[]{new Argument("{id}", Integer.class)});
    }

    @Override
    public Responce execute(Request r){
        try {
            this.getCollectionManager().removeById((int) r.getArgs().get(this.getUsage()[0].getName()));
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды remove_by_id");
        }
    }
}
