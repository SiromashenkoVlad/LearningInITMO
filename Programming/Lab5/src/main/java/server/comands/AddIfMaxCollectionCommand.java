package server.comands;

import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class AddIfMaxCollectionCommand extends CollectionCommand {
    public AddIfMaxCollectionCommand(CollectionManager collectionManager){
        super(collectionManager, "add_if_max",  "добавить новый элемент в коллекцию, " +
                "если его значение превышает значение наибольшего элемента этой коллекции", new Argument[]{
                        new Argument("{element}", Person.class)});
    }

    public Responce execute(Request r){
        try {
            String nameArgument = this.getNameArgumentByIndex(0);
            Person p = (Person) r.defineArgByName(nameArgument);
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
