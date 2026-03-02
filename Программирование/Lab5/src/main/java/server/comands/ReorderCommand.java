package server.comands;

import common.Enums.Commands;
import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ReorderCommand extends Command implements WithoutArguments {
    CollectionManager collectionManager;

    public ReorderCommand(CollectionManager collectionManager){
        super("reorder", "отсортировать коллекцию в порядке, обратном нынешнему", "");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try {
            collectionManager.reorder();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды reorder");
        }
    }
}
