package server.comands;

import common.Enums.Commands;
import common.Mainpart.Person;
import server.managers.CollectionManager;

public class AddIfMaxCommand extends Command{
    CollectionManager collectionManager;
    public AddIfMaxCommand(CollectionManager collectionManager){
        super("add_if_max",  "добавить новый элемент в коллекцию, " +
                "если его значение превышает значение наибольшего элемента этой коллекции", "{element}");
        this.collectionManager = collectionManager;
    }

    public void execute(Person p){
        Person mxpers = collectionManager.getMax();
        if (mxpers.getId() < p.getId()){
            collectionManager.add(p);
        }
    }
}
