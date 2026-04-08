package server;

import common.requests.Request;
import common.requests.Responce;
import server.managers.Communicator;
import server.managers.ConnectionManager;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        try(ConnectionManager connectionManager = new ConnectionManager(3418)){
            while(true) {
                connectionManager.acceptClient();
                String filename = connectionManager.gettingFile();
                while (!filename.endsWith(".csv")) {
                    connectionManager.sendingResponse(new Responce(false, null));
                    filename = connectionManager.gettingFile();
                }

                Communicator communicator = new Communicator(filename);
                connectionManager.sendingResponse((Serializable) communicator.getUsagesCommands());

                while (true) {
                    try {
                        Request r = connectionManager.gettingRequest();
                        Responce resp = communicator.call(r);
                        connectionManager.sendingResponse(resp);
                    } catch (EOFException e) {
                        System.out.println("Клиент отключился");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер на порту " + e.getMessage());
        } catch (ClassNotFoundException e){
            System.err.println("Ошибка чтения запроса" + e.getMessage());
        }

    }
}
