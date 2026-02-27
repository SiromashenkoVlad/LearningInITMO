package server.comands;

import common.Enums.Commands;
import common.Mainpart.Person;
import common.Model.Location;
import server.managers.CollectionManager;

import java.util.Objects;

public class CountByLocationCommand extends Command{
    CollectionManager collectionManager;

    public CountByLocationCommand(CollectionManager collectionManager){
        super("count_by_location", "вывести количество элементов," +
                " значение поля location которых равно заданному", "location");
        this.collectionManager = collectionManager;
    }

    public String execute(Location location) {
        long count = collectionManager.getCollection()
                .stream()
                .filter(p -> Objects.equals(p.getLocation(), location))
                .count();

        return String.valueOf(count);
    }
}
