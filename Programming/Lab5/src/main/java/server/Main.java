package server;

import server.Connection.ConnectionManager;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        try(ConnectionManager connectionManager = new ConnectionManager(3418)){

        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер на порту " + e.getMessage());
        } catch (ClassNotFoundException e){
            System.err.println("Ошибка чтения запроса" + e.getMessage());
        }

    }
}
