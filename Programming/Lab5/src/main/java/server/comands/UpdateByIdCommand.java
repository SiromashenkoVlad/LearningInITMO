package server.comands;

import common.mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class UpdateByIdCommand extends CommandCollection {
    public UpdateByIdCommand(CollectionManager collectionManager){
        super(collectionManager, "update", "обновит значение элемента коллекции, id которого равен заданному",
                new Argument[]{new Argument("{id}", Integer.class), new Argument("{element}", Person.class)});
    }

    @Override
    public Responce execute(Request r){
        try{
            getCollectionManager().updateById((int)r.getArgs().get(this.getUsage()[0].getName()),
                    (Person) r.getArgs().get(this.getUsage()[1].getName()));
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды update id");
        }
    }
}
