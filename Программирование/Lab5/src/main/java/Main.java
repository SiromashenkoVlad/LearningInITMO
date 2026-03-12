import client.Console.StandartConsole;
import client.Interrogator;
import client.Runner;
import server.managers.Communicator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        if (args.length == 0) {
            System.out.println("Не указано имя файла");
            return;
        }

        String fileName = args[0] + ".csv";
        Runner runner = new Runner(new StandartConsole(), new Interrogator(new Scanner(System.in)),
                new Communicator(fileName));
        runner.interactiveMode();
    }
}
