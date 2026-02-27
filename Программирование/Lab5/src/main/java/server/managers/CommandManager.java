package server.managers;

import common.Enums.Commands;
import server.comands.Command;

import java.util.EnumMap;
import java.util.Map;

public class CommandManager {
    private final Map<Commands, Command> commands =
            new EnumMap<>(Commands.class);

    public void register (Commands name, Command command){
        commands.put(name, command);
    }

    public Map<Commands, Command> getCommands(){
        return commands;
    }
}
