package server.comands;

import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class SaveCommand extends Command implements WithoutArguments {
    CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager){
        super("save", "сохранит коллекцию в файл", "");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try {
            collectionManager.save();
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды save");
        }
    }
}
