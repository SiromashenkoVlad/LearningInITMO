package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.db.dao.UserDao;

public class RegisterCommand extends Command{
    public RegisterCommand(){
        super("reg", "Зарегистрирует нового юзера", new Argument[0]);
    }

    @Override
    public Responce execute(Request r){
        UserDao userDao = UserDao.getInstance();
        if(userDao.save(r.getCredentialsProvider()).isPresent()){
            return new Responce(true, "Регистрация прошла успешно");
        }
        return new Responce(false, "Регистрация не удалась попробуйте использовать другой логин");
    }
}
