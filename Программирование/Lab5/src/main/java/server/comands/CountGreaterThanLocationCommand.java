package server.comands;

import common.Intefaces.Locationable;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class CountGreaterThanLocationCommand extends Command implements Locationable {
    CollectionManager collectionManager;

    public CountGreaterThanLocationCommand(CollectionManager collectionManager){
        super("count_greater_than_location", "вывести количество элементов," +
                " значение поля location которых больше заданного", "location");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r) {
        try{
            long count = collectionManager.getCollection().stream().filter(p -> p.getLocation() != null)
                    .filter(p -> p.getLocation().compareTo(r.getLocation()) > 0).count();
            return new Responce(true, String.valueOf(count));
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды count_by_location");
        }
    }
}
