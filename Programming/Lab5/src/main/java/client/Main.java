package client;

import client.console.StandartConsole;
import common.enums.WorkMode;
import common.exceptions.DisconnectFromServer;
import common.mainpart.Person;
import common.requests.Argument;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        if (args.length == 0) {
            System.out.println("Не указано имя файла");
            return;
        }

        String fileName = args[0] + ".csv";
        for (int i = 2; i < 15; i += 2){
            try(ConnectionManager connectionManager = new ConnectionManager(InetAddress.getByName("127.0.0.1"), 8080)) {
                connectionManager.sendingRequest(fileName);
                Person.setNextId((Integer) connectionManager.gettingResponse());
                Map<String, Argument[]> usages = (Map<String, Argument[]>) connectionManager.gettingResponse();
                System.out.println("Типо принял usage");
                Runner runner = new Runner(new StandartConsole(), new Interrogator(new Scanner(System.in)),
                        usages, connectionManager);

                runner.interactiveMode(WorkMode.Interactive);
                break;
            } catch (IOException e){
                System.err.println("Сервер временно недоступен, пробую подключиться");
                System.err.println(e.getMessage());
                try{
                    Thread.sleep(i * 1000);
                } catch (InterruptedException ex) {

                }
            } catch (ClassNotFoundException e) {
                System.err.println("Не смог прочитать usages");
            } catch (DisconnectFromServer e){
                System.err.println("Пробую переподключиться");
            }
        }
        System.out.println("Подключение закрыто");
    }
}
