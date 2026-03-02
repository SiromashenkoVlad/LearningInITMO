package server.comands;

import common.Intefaces.Personable;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class AddCommand extends Command implements Personable {
    CollectionManager collectionManager;
    public AddCommand(CollectionManager collectionManager){
        super("add", "добавит новый элемент в коллекцию", "{element}");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try {
            collectionManager.add(r.getPerson());
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды add");
        }
    }
}
