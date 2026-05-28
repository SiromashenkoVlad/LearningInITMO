package client;

import client.console.Console;
import client.console.StandartConsole;
import client.readArguments.ValueFactory;
import common.enums.WorkMode;
import common.exceptions.DisconnectFromServer;
import common.Mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

public class Runner{
    public enum ExitCode {
        OK,
        EXIT,
        ERROR
    }

    private final Console console;
    private Interrogator interrogator;
    private Map<String, Argument[]> usageCommands;
    private ConnectionManager connectionManager;
    private Set<String> setFiles = new HashSet<>();

    public Runner(Console console, Interrogator interrogator, ConnectionManager connectionManager, Map<String, Argument[]> usage){
        this.console = console;
        this.interrogator = interrogator;
        this.connectionManager = connectionManager;
        this.usageCommands = usage;
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

    public ExitCode launchCommand(String userCommand) {
        if (userCommand.isEmpty()){
            return ExitCode.OK;
        }

        switch (userCommand) {
            case "exit" -> {
                return exit(userCommand);
            }
            case "execute_script" -> {
                return execute_Script(userCommand);
            }

            default -> {
                return defaultCommand(userCommand);
            }
        }
    }

    public Responce sendRequest(Request r) throws IOException, ClassNotFoundException {
        connectionManager.sendingRequest(r);
        return (Responce) connectionManager.gettingResponse();
    }

    public void outAnswer(Responce r){
        console.println(r.getAnswer());
        if (r.getCollection() != null){
            console.println(r.getCollection().stream().map(Person::toString)
                    .collect(Collectors.joining("\n")));
        }
    }

    public ExitCode exit(String userCommand){
        try{
            Responce r = sendRequest(new Request(userCommand, new HashMap<>()));
            outAnswer(r);
            return ExitCode.EXIT;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Запрос на выход не отправлен, возможна потеря данных последней сессии");
            throw new DisconnectFromServer(e.getMessage());
        }
    }

    public ExitCode execute_Script(String userCommand){
        ValueFactory valueFactory = new ValueFactory();
        String file = (String) valueFactory.getReader(String.class).read(console, interrogator);
        if (setFiles.contains(file)){
            return ExitCode.ERROR;
        }
        else {
            setFiles.add(file);
            scriptMode(file);
            return ExitCode.OK;
        }
    }

    public ExitCode defaultCommand(String userCommand){
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

        try{
            Responce r = sendRequest(new Request(userCommand, args));
            outAnswer(r);
            return ExitCode.OK;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Запрос не отправлен " + e.getMessage());
            throw new DisconnectFromServer(e.getMessage());
        }
    }

    private void setUsageCommands(Map<String, Argument[]> usageCommands){
        this.usageCommands = usageCommands;
    }

    private void setConnectionManager(ConnectionManager conn){
        this.connectionManager = conn;
    }
}