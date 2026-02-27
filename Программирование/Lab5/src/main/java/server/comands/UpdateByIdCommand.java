package server.comands;

import common.Enums.Commands;
import common.Mainpart.Person;
import server.managers.CollectionManager;

public class UpdateByIdCommand extends Command{
    CollectionManager collectionManager;
    public UpdateByIdCommand(CollectionManager collectionManager){
        super("update id", "обновит значение элемента коллекции, id которого равен заданному",
                "{element}");
        this.collectionManager = collectionManager;
    }

    public void execute(int id, Person p){
        collectionManager.updateById(id, p);
    }
}
