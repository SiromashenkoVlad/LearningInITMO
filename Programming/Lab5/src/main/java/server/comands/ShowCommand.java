package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ShowCommand extends CommandCollection {
    public ShowCommand(CollectionManager collectionManager){
        super(collectionManager, "show", "выводит в стандартный поток вывода" +
                " все элементы коллекции в строковом представлении", new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        try{
            return new Responce(true, "Коллекция:\n", this.getCollectionManager().getCollection());
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды show");
        }
    }
}
