package server.comands;

import common.model.Location;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

import java.util.Objects;

public class CountByLocationCommand extends CommandCollection {
    public CountByLocationCommand(CollectionManager collectionManager){
        super(collectionManager, "count_by_location", "вывести количество элементов," +
                " значение поля location которых равно заданному", new Argument[]{
                        new Argument("{location}", Location.class)});
    }

    @Override
    public Responce execute(Request r) {
        try{
            long count = this.getCollectionManager().getCollection()
                    .stream()
                    .filter(p -> Objects.equals(p.getLocation(),
                            (Location) r.getArgs().get(this.getUsage()[0].getName()))).count();

            return new Responce(true, String.valueOf(count));
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды count_by_location");
        }
    }
}
