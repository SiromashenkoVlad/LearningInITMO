package client;

import client.Console.StandartConsole;
import common.Intefaces.OnesStringArgumentable;
import common.Intefaces.Personable;
import common.Mainpart.Person;
import common.Model.Location;
import common.requests.Request;
import server.comands.Command;
import server.managers.RequestHandler;

import java.util.Map;
import java.util.Stack;

public class Runner {
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final StandartConsole console;
    private final Interrogator interrogator;
    private final RequestHandler requestHandler;
    private final Stack<Command> stack = new Stack<Command>(); // развлечения под скрипт

    public Runner(StandartConsole console, Interrogator interrogator, RequestHandler requestHandler){
        this.console = console;
        this.interrogator = interrogator;
        this.requestHandler = requestHandler;
    }

    public void interactiveMode(){
        console.println("Начало работы в интерактивном режиме. Добро пожаловать");
        ExitCode commandStatus = null;
        do {
            console.ps1();
            String userCommand = interrogator.getUserScanner().next().trim();
            commandStatus = launchCommand(userCommand);
        } while (commandStatus != ExitCode.EXIT);
    }

    private ExitCode launchCommand(String userCommand){
        if (userCommand.isEmpty()){
            return ExitCode.OK;
        }

        Map<String, Command> availableCommands = requestHandler.getCommands();
        if (availableCommands.containsKey(userCommand)){
            console.println("Команды " + userCommand + " не существует. Вызовите команду help для" +
                    " просмотра доступных команд");
            return ExitCode.OK;
        }

        switch (userCommand) { // разделить на ввод person и location
            case "exit" -> {
                if (!requestHandler.callCommand(new Request(userCommand, -1, null, null, null))
                        .isSuccess()) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (чек на рекурсию) return ExitCode.ERROR;
                else return scriptMode(userCommand);
            }
            default -> {

                if () return ExitCode.ERROR;
            }
        };
        return ExitCode.OK;
    }

    public Request readArguments(String userCommand, Map<String, Command> availableCommands){
        int id; String filename;
        Person p; Location location;

        if (availableCommands.get(userCommand) instanceof OnesStringArgumentable){
            if (interrogator.getUserScanner().hasNextInt()){
                id = interrogator.getUserScanner().nextInt();
            }
            if (interrogator.getUserScanner().hasNext()){
                filename = interrogator.getUserScanner().next();
            }
        }
        if (availableCommands.get(userCommand) instanceof Personable){
            console.println("Начался ввод ");
            console.println("Введите id ");
        }

    }
}
