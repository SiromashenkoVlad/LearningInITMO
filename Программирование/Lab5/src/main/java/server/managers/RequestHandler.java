package server.managers;

import common.requests.Request;
import common.requests.Responce;
import server.comands.Command;

import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public void register(String name, Command command){
        commands.put(name, command);
    }

    public Map<String, Command> getCommands(){
        return commands;
    }

    public Responce callCommand(Request r){
        return commands.get(r.getName()).execute(r);
    }
}
