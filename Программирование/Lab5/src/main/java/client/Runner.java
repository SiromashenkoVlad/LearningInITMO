package client;

import client.Console.StandartConsole;
import common.Enums.Commands;

import java.util.Scanner;

public class Runner {
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final StandartConsole console;
    private final Interrogator interrogator;

    public Runner(StandartConsole console, Interrogator interrogator){
        this.console = console;
        this.interrogator = interrogator;
    }

    public void interactiveMode(){
        console.println("Начало работы в интерактивном режиме. Добро пожаловать");
        Scanner input = interrogator.getUserScanner();
        ExitCode commandStatus = null;
        do {
            console.ps1();
            String[] userCommand = (input.nextLine().trim() + "  ").split(" ", 3);
            userCommand[1] = userCommand[1].trim();

            commandStatus = launchCommand(userCommand);
        } while (commandStatus != ExitCode.EXIT);
    }

    private ExitCode launchCommand(String[] userCommand){
        if (userCommand[0].isEmpty()){
            return ExitCode.OK;
        }
        Commands command = Commands.valueOf(userCommand[0]);
        if (command == null){
            console.println("Команды " + userCommand[0] + " не существует. Вызовите команду help для" +
                    " просмотра доступных команд");
            return ExitCode.OK;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if () return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if () return ExitCode.ERROR;
                else return scriptMode(userCommand[1]);
            }
            default -> { if () return ExitCode.ERROR; }
        };

        return ExitCode.OK;
    }
}
