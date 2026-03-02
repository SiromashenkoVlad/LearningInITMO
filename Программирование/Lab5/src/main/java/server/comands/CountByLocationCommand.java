package server.comands;

import common.Intefaces.Locationable;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

import java.util.Objects;

public class CountByLocationCommand extends Command implements Locationable {
    CollectionManager collectionManager;

    public CountByLocationCommand(CollectionManager collectionManager){
        super("count_by_location", "вывести количество элементов," +
                " значение поля location которых равно заданному", "location");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r) {
        try{
            long count = collectionManager.getCollection()
                    .stream()
                    .filter(p -> Objects.equals(p.getLocation(), r.getLocation()))
                    .count();

            return new Responce(true, String.valueOf(count));
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды count_by_location");
        }
    }
}
