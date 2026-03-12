package server.comands;

import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class AddIfMaxCommand extends CommandCollection {
    public AddIfMaxCommand(CollectionManager collectionManager){
        super(collectionManager, "add_if_max",  "добавить новый элемент в коллекцию, " +
                "если его значение превышает значение наибольшего элемента этой коллекции", new Argument[]{
                        new Argument("{element}", Person.class)});
    }

    public Responce execute(Request r){
        try {
            Person p = (Person) r.getArgs().get(this.getUsage()[0]);
            Person mxpers = this.getCollectionManager().getMax();
            if (mxpers.getId() < p.getId()){
                this.getCollectionManager().add(p);
            }
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды add_if_max");
        }
    }
}
