package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;
import server.managers.CommandManager;

import java.util.HashMap;

public class HelpCommand extends Command{
    CommandManager collection;
    public HelpCommand(CommandManager collectionManager){
        super("help", "выводит справку по доступным командам", "");
        collection = collectionManager;
    }

    public String execute(){
        StringBuilder answer = new StringBuilder();
        for (Command command : collection.getCommands().values()){
            answer.append(command.getName()).append(" ").append(command.getDescription());
        }
        return answer.toString();
    }
}
