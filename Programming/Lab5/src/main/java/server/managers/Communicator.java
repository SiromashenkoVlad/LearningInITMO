package server.managers;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.comands.Command;
import server.comands.CommandCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Communicator {
    private final Map<String, Command> commands; // будет ли без generic работать?
    private final Map<String, Argument[]> usagesCommands;
    private final WorkManager workManager;

    public Communicator(String filename){
        this.workManager = new WorkManager(new CollectionManager(filename));
        this.usagesCommands = workManager.getUsagesCommands();
        this.commands = workManager.getCommands();
    }

    public Map<String, Command> getCommands(){
        return commands;
    }

    public Map<String, Argument[]> getUsagesCommands(){
        return usagesCommands;
    }

    public Responce call(Request r){
        return workManager.callCommand(r);
    }
}
