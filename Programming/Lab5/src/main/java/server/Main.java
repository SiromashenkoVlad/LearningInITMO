package server;

import java.io.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try(Server serv = new Server()){
            serv.start(18234);
        } catch (IOException | SQLException e) {
            System.err.println("Не удалось запустить сервер на порту " + e.getMessage());
        }
    }
}
