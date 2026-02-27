package server.comands;

import common.Enums.Commands;
import common.Model.Location;
import server.managers.CollectionManager;

public class CountGreaterThanLocationCommand extends Command{
    CollectionManager collectionManager;

    public CountGreaterThanLocationCommand(CollectionManager collectionManager){
        super("count_greater_than_location", "вывести количество элементов," +
                " значение поля location которых больше заданного", "location");
        this.collectionManager = collectionManager;
    }

    public String execute(Location l){
        long count = collectionManager.getCollection().stream().filter(p -> p.getLocation() != null)
                .filter(p -> p.getLocation().compareTo(l) > 0).count();
        return String.valueOf(count);
    }
}
