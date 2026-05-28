package client;

import client.console.StandartConsole;
import common.Mainpart.Person;
import common.enums.WorkMode;
import common.exceptions.DisconnectFromServer;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Scanner;

public class EstablishConnection {
    public Request r = null;

    public void run(String fileName){
        for (int i = 2; i < 15; i += 2){
            try(ConnectionManager connectionManager = new ConnectionManager(InetAddress.getByName("localhost"), 8080)) {
                setNextId(connectionManager, fileName);

                Map<String, Argument[]> usages = (Map<String, Argument[]>) connectionManager.gettingResponse();
                System.out.println("Типо принял usage");
                Runner runner = new Runner(new StandartConsole(), new Interrogator(new Scanner(System.in)),
                        connectionManager, usages);
                if (r != null){
                    Responce rep = runner.sendRequest(r);
                    runner.outAnswer(rep);
                    r = null;
                }

                runner.interactiveMode(WorkMode.Interactive);
                break;
            } catch (IOException e){
                System.err.println("Сервер временно недоступен, пробую подключиться");
                System.err.println(e.getMessage());
                try{
                    Thread.sleep(i * 1000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Не смог прочитать usages");
            } catch (DisconnectFromServer e){
                System.err.println("Пробую переподключиться");
                r = e.getLastRequest();
            }
        }
        System.out.println("Подключение закрыто");
    }

    private void setNextId(ConnectionManager connectionManager, String fileName)
            throws IOException, ClassNotFoundException {
        connectionManager.sendingRequest(fileName);
        Integer nextId = (Integer) connectionManager.gettingResponse();
        System.out.println("Прочитал значение id: " + nextId);
        Person.setNextId(nextId);
    }
}
