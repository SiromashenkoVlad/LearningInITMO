package server.comands;

import common.Intefaces.OnesStringArgumentable;
import common.Intefaces.Personable;
import common.Mainpart.Person;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class AddIfMaxCommand extends Command implements OnesStringArgumentable, Personable {
    CollectionManager collectionManager;
    public AddIfMaxCommand(CollectionManager collectionManager){
        super("add_if_max",  "добавить новый элемент в коллекцию, " +
                "если его значение превышает значение наибольшего элемента этой коллекции", "{element}");
        this.collectionManager = collectionManager;
    }

    public Responce execute(Request r){
        try {
            Person p = r.getPerson();
            Person mxpers = collectionManager.getMax();
            if (mxpers.getId() < p.getId()){
                collectionManager.add(p);
            }
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды add_if_max");
        }
    }
}
