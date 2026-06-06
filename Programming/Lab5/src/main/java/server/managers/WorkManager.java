package server.managers;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.comands.*;

import java.util.HashMap;
import java.util.Map;

public class WorkManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, Argument[]> usagesCommands = new HashMap<>();
    private final CollectionManager collectionManager;
    private final static Logger LOGGER = LogManager.getLogger(WorkManager.class);

    private static WorkManager instance = null;

    public static WorkManager getInstance(CollectionManager collectionManager) {
        if (instance == null) {
            synchronized (WorkManager.class) {
                if (instance == null) {
                    instance = new WorkManager(collectionManager);
                }
            }
        }
        return instance;
    }

    public static WorkManager getInstance() {
        if (instance == null) throw new IllegalStateException("WorkManager не инициализирован");
        return instance;
    }

    private WorkManager(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

        this.addCommand("info", new InfoCollectionCommand(collectionManager));
        this.addCommand("exit", new ExitCommand());
        this.addCommand("show", new ShowCollectionCommand(collectionManager));
        this.addCommand("add", new AddCollectionCommand(collectionManager));
        this.addCommand("update", new UpdateByIdCollectionCommand(collectionManager));
        this.addCommand("remove_by_id", new RemoveByIdCollectionCommand(collectionManager));
        this.addCommand("clear", new ClearCollectionCommand(collectionManager));

        this.addCommand("add_if_max", new AddIfMaxCollectionCommand(collectionManager));
        this.addCommand("shuffle", new ShuffleCollectionCommand(collectionManager));
        this.addCommand("reorder", new ReorderCollectionCommand(collectionManager));
        this.addCommand("count_by_location", new CountByLocationCollectionCommand(collectionManager));
        this.addCommand("count_greater_than_location", new CountGreaterThanLocationCollectionCommand(collectionManager));
        this.addCommand("print_field_descending_eye_color", new FieldDescendingEyeColorCollectionCommand(collectionManager));
        this.addCommand("help", new HelpCommand(commands));
        this.addServCommand("log", new LoginCommand());
        this.addServCommand("reg", new RegisterCommand());
        LOGGER.info("Создали WorkManager");
    }

    private void addCommand(String name, Command command){
        this.commands.put(name, command);
        this.usagesCommands.put(name, command.getUsage());
    }

    private void addServCommand(String name, Command command){
        this.commands.put(name, command);
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
