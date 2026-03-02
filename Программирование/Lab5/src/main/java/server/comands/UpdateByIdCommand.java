package server.comands;

import common.Intefaces.OnesStringArgumentable;
import common.Intefaces.Personable;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class UpdateByIdCommand extends Command implements OnesStringArgumentable, Personable {
    CollectionManager collectionManager;
    public UpdateByIdCommand(CollectionManager collectionManager){
        super("update id", "обновит значение элемента коллекции, id которого равен заданному",
                "{element}");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try{
            collectionManager.updateById(r.getId(), r.getPerson());
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды update id");
        }
    }
}
