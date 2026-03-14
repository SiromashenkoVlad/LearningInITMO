package client;

import client.console.Console;
import client.readArguments.ValueFactory;
import common.enums.WorkMode;
import common.requests.Argument;
import common.requests.Request;
import server.managers.Communicator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Runner {
    public enum ExitCode {
        OK,
        EXIT,
        ERROR
    }

    private final Console console;
    private Interrogator interrogator;
    private final Communicator communicator;
    private Map<String, Argument[]> usageCommands;
    private Set<String> setFiles = new HashSet<>();

    public Runner(Console console, Interrogator interrogator, Communicator communicator){
        this.console = console;
        this.interrogator = interrogator;
        this.communicator = communicator;
        this.usageCommands = communicator.getUsagesCommands();
    }

    public void interactiveMode(WorkMode workMode){
        console.println("Work mode: " + workMode.name() + ". Welcome");
        ExitCode commandStatus = null;
        do {
            console.ps1();
            if (!interrogator.getUserScanner().hasNext()){ return; }
            String userCommand = interrogator.getUserScanner().next().trim();
            commandStatus = launchCommand(userCommand);
        } while (commandStatus != ExitCode.EXIT);
    }

    public void scriptMode(String fileName){
        try {
            Interrogator newInterrogator = new Interrogator(new Scanner(new File(fileName)));
            Interrogator oldInterrogator = interrogator;
            interrogator = newInterrogator;
            interactiveMode(WorkMode.File);
            interrogator = oldInterrogator;
        } catch (FileNotFoundException e){
            console.println("Такого файла или нет или доступ к нему закрыт");
        }
    }

    private ExitCode launchCommand(String userCommand){
        if (userCommand.isEmpty()){
            return ExitCode.OK;
        }

        switch (userCommand) {
            case "exit" -> {
                console.println(communicator.call(new Request(userCommand, new HashMap<>())).getAnswer());
                return ExitCode.EXIT;
            }
            case "execute_script" -> {
                ValueFactory valueFactory = new ValueFactory();
                String file = (String) valueFactory.getReader(String.class).read(console, interrogator);
                if (setFiles.contains(file)) return ExitCode.ERROR;
                else {
                    setFiles.add(file);
                    scriptMode(file);
                    return ExitCode.OK;
                }
            }
            default -> {
                if (!usageCommands.containsKey(userCommand)){

                    console.println("Команды " + userCommand + " не существует. Вызовите команду help для" +
                            " просмотра доступных команд");
                    return ExitCode.OK;
                }

                Map<String,Object> args = new HashMap<>();
                ValueFactory valueFactory = new ValueFactory();
                for (Argument arg : usageCommands.get(userCommand)) {
                    Object value = valueFactory.getReader(arg.getType()).read(console, interrogator);
                    args.put(arg.getName(), value);
                }

                console.println(communicator.call(new Request(userCommand, args)).getAnswer());
            }
        }
        return ExitCode.OK;
    }
}
