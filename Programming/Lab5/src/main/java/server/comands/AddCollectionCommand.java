package server.comands;

import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class AddCollectionCommand extends CollectionCommand {
    public AddCollectionCommand(CollectionManager collectionManager){
        super(collectionManager,"add", "добавит новый элемент в коллекцию", new Argument[]{
                new Argument("{element}", Person.class)});
    }

    @Override
    public Responce execute(Request r){
        try {
            String nameArgument = this.getNameArgumentByIndex(0);
            Person p = (Person) r.defineArgByName(nameArgument);
            p.addMaker(r.getCredentialsProvider().getLogin());
            this.getCollectionManager().add(p);
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды add");
        }
    }
}
