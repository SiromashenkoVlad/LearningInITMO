package client;

import client.console.StandartConsole;
import common.enums.WorkMode;
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
        try(ConnectionManager connectionManager = new ConnectionManager(InetAddress.getLocalHost(), 3418)) {
            connectionManager.sendingRequest(fileName);
            Map<String, Argument[]> usages = (Map<String, Argument[]>) connectionManager.gettingResponse();
            Runner runner = new Runner(new StandartConsole(), new Interrogator(new Scanner(System.in)),
                    usages, connectionManager);
            runner.interactiveMode(WorkMode.Interactive);
        } catch (IOException e){
            System.err.println("Ошибка создания подключения");
        } catch (ClassNotFoundException e) {
            System.err.println("Не смог прочитать usages");
        }
    }
}
