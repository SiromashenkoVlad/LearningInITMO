package server.managers;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.comands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, Argument[]> usagesCommands = new HashMap<>();
    private final CollectionManager collectionManager;

    public WorkManager(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

        this.addCommand("info", new InfoCommand(collectionManager));
        this.addCommand("show", new ShowCommand(collectionManager));
        this.addCommand("add", new AddCommand(collectionManager));
        this.addCommand("update", new UpdateByIdCommand(collectionManager));
        this.addCommand("remove_by_id", new RemoveByIdCommand(collectionManager));
        this.addCommand("clear", new ClearCommand(collectionManager));
        this.addCommand("save", new SaveCommand(collectionManager));
        this.addCommand("add_if_max", new AddIfMaxCommand(collectionManager));
        this.addCommand("shuffle", new ShuffleCommand(collectionManager));
        this.addCommand("reorder", new ReorderCommand(collectionManager));
        this.addCommand("count_by_location", new CountByLocationCommand(collectionManager));
        this.addCommand("count_greater_than_location", new CountGreaterThanLocationCommand(collectionManager));
        this.addCommand("print_field_descending_eye_color", new FieldDescendingEyeColorCommand(collectionManager));
        this.addCommand("help", new HelpCommand(commands));
    }

    private void addCommand(String name, Command command){
        this.commands.put(name, command);
        this.usagesCommands.put(name, command.getUsage());
    }

    public Responce callCommand(Request r){
        return commands.get(r.getName()).execute(r);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public Map<String, Argument[]> getUsagesCommands(){
        return usagesCommands;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
