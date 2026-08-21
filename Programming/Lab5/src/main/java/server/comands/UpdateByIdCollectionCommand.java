package server.comands;

import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.db.dao.PersonDao;
import server.db.dao.UserDao;
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
            int id = (Integer) r.defineArgByName(this.getNameArgumentByIndex(0));
            Person p = (Person) r.defineArgByName(this.getNameArgumentByIndex(1));
            PersonDao personDao = PersonDao.getInstance();
            String login = r.getCredentialsProvider().getLogin();
            UserDao userDao = UserDao.getInstance();
            if (!Objects.equals(login, personDao.readMaker(id).get()) & !userDao.readListAdmin().contains(login))
                return new Responce(false, "У вас нет прав на изменение этого элемента");
            getCollectionManager().updateById(id, p);
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды update id");
        }
    }
}
