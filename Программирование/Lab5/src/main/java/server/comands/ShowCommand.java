package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;

public class ShowCommand extends Command {
    CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager){
        super("show", "выводит в стандартный поток вывода" +
                " все элементы коллекции в строковом представлении", "");
        this.collectionManager = collectionManager;
    }

    public String execute(){
        return collectionManager.toString();
    }
}
