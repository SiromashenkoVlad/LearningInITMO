package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;

import java.util.Arrays;
import java.util.Map;

public class HelpCommand extends Command {
    private final Map<String, Command> commands;
    public HelpCommand(Map<String, Command> commands){
        super("help", "выводит справку по доступным командам", new Argument[0]);
        this.commands = commands;
    }

    @Override
    public Responce execute(Request r){
        try{
            StringBuilder answer = new StringBuilder();
            for (Command command : commands.values()){
                answer.append("\n").append(command.getName()).append(" ").append(command.usageString()).append(" - ").append(command.getDescription());
            }
            return new Responce(true, answer.toString());
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды help");
        }
    }
}
