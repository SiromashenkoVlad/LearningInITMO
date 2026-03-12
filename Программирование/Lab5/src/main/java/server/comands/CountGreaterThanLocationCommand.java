package server.comands;

import common.Model.Location;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class CountGreaterThanLocationCommand extends CommandCollection {
    public CountGreaterThanLocationCommand(CollectionManager collectionManager){
        super(collectionManager,"count_greater_than_location", "вывести количество элементов," +
                " значение поля location которых больше заданного", new Argument[]{
                        new Argument("{location}", Location.class)});
    }

    @Override
    public Responce execute(Request r) {
        try{
            long count = this.getCollectionManager().getCollection().stream().filter(
                    p -> p.getLocation() != null).filter(p -> p.getLocation().compareTo(
                                    (Location) r.getArgs().get(this.getUsage()[0].getName())) > 0).count();
            return new Responce(true, String.valueOf(count));
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды count_by_location");
        }
    }
}
