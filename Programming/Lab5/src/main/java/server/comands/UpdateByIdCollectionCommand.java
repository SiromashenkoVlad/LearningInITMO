package server.comands;

import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.db.dao.PersonDao;
import server.managers.CollectionManager;

import java.util.Objects;

public class UpdateByIdCollectionCommand extends CollectionCommand {
    public UpdateByIdCollectionCommand(CollectionManager collectionManager){
        super(collectionManager, "update", "обновит значение элемента коллекции, id которого равен заданному",
                new Argument[]{new Argument("{id}", Integer.class), new Argument("{element}", Person.class)});
    }

    @Override
    public Responce execute(Request r){
        try{
            System.out.println("Запрос на обновление");
            if (!Objects.equals(r.getCredentialsProvider().getLogin(), PersonDao.readMaker(
                    (int) r.getArgs().get(this.getUsage()[0].getName())).get()))
                return new Responce(false, "У вас нет прав на изменение этого элемента");
            getCollectionManager().updateById((int)r.getArgs().get(this.getUsage()[0].getName()),
                    (Person) r.getArgs().get(this.getUsage()[1].getName()));
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды update id");
        }
    }
}
