package server.comands;

import common.Enums.Commands;
import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class ShowCommand extends Command implements WithoutArguments {
    CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager){
        super("show", "выводит в стандартный поток вывода" +
                " все элементы коллекции в строковом представлении", "");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try{
            return new Responce(true, collectionManager.toString());
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды show");
        }
    }
}
