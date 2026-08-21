package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.db.dao.PersonDao;
import server.db.dao.UserDao;
import server.managers.CollectionManager;

import java.util.Objects;

public class RemoveByIdCollectionCommand extends CollectionCommand {
    public RemoveByIdCollectionCommand(CollectionManager collectionManager){
        super(collectionManager, "remove_by_id", "удалит элемент из коллекции по его id",
                new Argument[]{new Argument("{id}", Integer.class)});
    }

    @Override
    public Responce execute(Request r){
        if (r.getCredentialsProvider() == null)
            return new Responce(false, "Вы не авторизовались для выполнения этого запроса");
        try {
            PersonDao personDao = PersonDao.getInstance();
            int id = (Integer) r.defineArgByName(this.getNameArgumentByIndex(0));
            UserDao userDao = UserDao.getInstance();
            String login = r.getCredentialsProvider().getLogin();
            System.out.println(!userDao.readListAdmin().contains(login));
            if (!Objects.equals(login, personDao.readMaker(id).get()) & !userDao.readListAdmin().contains(login))
                return new Responce(false, "У вас нет прав на изменение этого элемента");
            this.getCollectionManager().removeById((int) r.getArgs().get(this.getUsage()[0].getName()));
            return new Responce(true, "");
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды remove_by_id");
        }
    }
}
