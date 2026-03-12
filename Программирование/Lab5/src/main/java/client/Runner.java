package client;

import client.Console.Console;
import client.readArguments.ValueFactory;
import common.requests.Argument;
import common.requests.Request;
import server.comands.CommandCollection;
import server.managers.Communicator;

import java.util.*;

public class Runner {
    public enum ExitCode {
        OK,
        EXIT,
    }

    private final Console console;
    private final Interrogator interrogator;
    private final Communicator communicator;
    private Map<String, Argument[]> usageCommands;
    private Stack<CommandCollection> stack = new Stack<CommandCollection>(); // развлечения под скрипт

    public Runner(Console console, Interrogator interrogator, Communicator communicator){
        this.console = console;
        this.interrogator = interrogator;
        this.communicator = communicator;
        this.usageCommands = communicator.getUsagesCommands();
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
        if (userCommand.equals("exit")){
            return ExitCode.EXIT;
        }
        if (!usageCommands.containsKey(userCommand)){
            console.println("Команды " + userCommand + " не существует. Вызовите команду help для" +
                    " просмотра доступных команд");
            return ExitCode.OK;
        }

//        switch (userCommand) {
//            case "exit" -> {
//                if (!communicator.call(new Request(userCommand, null))
//                        .isSuccess()) return ExitCode.ERROR;
//                else return ExitCode.EXIT;
//                break;
//            }
//            case "execute_script" -> {
//                if (чек на рекурсию) return ExitCode.ERROR;
//                else return scriptMode(userCommand);
//                break;
//            }
//            default -> {
//                Map<String,Object> args = new HashMap<>();
//                ValueFactory valueFactory = new ValueFactory();
//                for (Argument arg : usageCommands.get(userCommand)) {
//                    Object value = valueFactory.getReader(arg.getType()).read(console, interrogator);
//                    args.put(arg.getName(), value);
//                }
//
//                communicator.call(new Request(userCommand, args));
//            }
//        };
        else {
            Map<String,Object> args = new HashMap<>();
                ValueFactory valueFactory = new ValueFactory();
                for (Argument arg : usageCommands.get(userCommand)) {
                    Object value = valueFactory.getReader(arg.getType()).read(console, interrogator);
                    args.put(arg.getName(), value);
                }

                console.println(communicator.call(new Request(userCommand, args)).getAnswer());
        }
        return ExitCode.OK;
    }
}
