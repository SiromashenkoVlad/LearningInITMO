package server.db.dao;

import common.userData.CredentialsProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.db.DataSource;
import server.db.HashPassword;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public final class UserDao implements SaveDao<CredentialsProvider, Optional<String>> {
    private final static Logger LOGGER = LogManager.getLogger(UserDao.class);
    private static UserDao instance = null;

    private UserDao(){}

    public static UserDao getInstance(){
        if (instance == null){
            instance = new UserDao();
        }
        return instance;
    }


    @Override
    public Optional<String> save(CredentialsProvider cp){
        String query = "insert into Users (name, password, salt) values (?, ?, ?)";
        try(Connection conn = SaveDao.getConnection();
            PreparedStatement pr = conn.prepareStatement(query)
        ){
            String salt = HashPassword.generateSalt();

            pr.setString(1, cp.getLogin());
            pr.setString(2, HashPassword.hashPassword(salt + cp.getPassword(), "sha-1"));
            pr.setString(3, salt);
            pr.executeUpdate();
            LOGGER.info("Добавлена строка в Users с login: " + cp.getLogin());
            return Optional.of(cp.getLogin());
        } catch (SQLException e){
            LOGGER.error(e);
            LOGGER.error(e.getSQLState());
            return Optional.empty();
        }
    }


    public boolean read(CredentialsProvider cp){
        try (Connection conn = SaveDao.getConnection();
             PreparedStatement pr = conn.prepareStatement("select * from Users where name = ?")
        ){
            pr.setString(1, cp.getLogin());
            ResultSet rs =  pr.executeQuery();
            boolean answer = rs.next();
            LOGGER.debug("Нашелся ли юзер по последнему запросу: " + answer);
            if (!answer){
                return false;
            }
            return answer;
        } catch (SQLException e){
            LOGGER.error(e);
            return false;
        }
    }

    public boolean checkUsersPassword(CredentialsProvider cp){
        try (Connection conn = SaveDao.getConnection();
             PreparedStatement pr = conn.prepareStatement("select * from Users where name = ?")
        ){
            pr.setString(1, cp.getLogin());
            ResultSet rs =  pr.executeQuery();
            if(!rs.next()){
                return false;
            }
            String salt = rs.getString("salt");
            String password = rs.getString("password");
            if (!HashPassword.hashPassword(salt + cp.getPassword(), "sha-1").equals(password)){
                return false;
            }
            LOGGER.debug("Юзер c определенным паролем по последнему запросу был найден");
            return true;
        } catch (SQLException e){
            LOGGER.error(e);
            return false;
        }
    }

    public HashSet<String> readListAdmin(){
        String query = "Select name from Users where role = true";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            HashSet<String> list = new HashSet<>();
            while (rs.next()){
                String login = rs.getString("name");
                list.add(login);
            }
            return list;
        }  catch (SQLException e) {
            LOGGER.error("Ошибка получения соединения", e);
            return new HashSet<>();
        }
    }
}
