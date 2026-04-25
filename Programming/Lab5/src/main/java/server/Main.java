package server;

import server.Connection.ConnectionManager;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        try(ConnectionManager connectionManager = new ConnectionManager(17554)){
            connectionManager.execute();
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер на порту " + e.getMessage());
        }

    }
}
