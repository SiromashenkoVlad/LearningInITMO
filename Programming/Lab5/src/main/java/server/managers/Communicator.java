package server.managers;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.comands.Command;

import java.sql.SQLException;
import java.util.Map;

public class Communicator {
    private final Map<String, Command> commands;
    private final Map<String, Argument[]> usagesCommands;
    private final WorkManager workManager;

    public Communicator() throws SQLException {
        this.workManager = WorkManager.getInstance(new CollectionManager());
        this.usagesCommands = workManager.getUsagesCommands();
        this.commands = workManager.getCommands();
    }

    public Map<String, Argument[]> getUsagesCommands(){
        return usagesCommands;
    }

    public Responce call(Request r){
        return workManager.callCommand(r);
    }
}
