package client;

import client.console.StandartConsole;
import common.enums.WorkMode;
import common.exceptions.DisconnectFromServer;
import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;

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
        new EstablishConnection().run(fileName);
    }
}
