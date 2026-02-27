package server.comands;

import common.Enums.Commands;
import common.Mainpart.Person;
import server.managers.CollectionManager;

public class AddCommand extends Command{
    CollectionManager collectionManager;
    public AddCommand(CollectionManager collectionManager){
        super("add", "добавит новый элемент в коллекцию", "{element}");
        this.collectionManager = collectionManager;
    }

    public void execute(Person p){
        collectionManager.add(p);
    }
}
