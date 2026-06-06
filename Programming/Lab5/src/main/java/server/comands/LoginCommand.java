package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.db.dao.UserDao;

public class LoginCommand extends Command{
    public LoginCommand(){
        super("log", "Залогинит пользователя", new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        if (r.getCredentialsProvider().getLogin() == null){
            return new Responce(false, "не указанал логин");
        }
        UserDao userDao = UserDao.getInstance();
        if(userDao.read(r.getCredentialsProvider())){
            if (!userDao.checkUsersPassword(r.getCredentialsProvider())){
                return new Responce(false, "Введенный пароль неверен");
            }
            return new Responce(true, "Вход совершен успешно");
        }
        return new Responce(false, "Такого пользователя не существует");
    }
}
