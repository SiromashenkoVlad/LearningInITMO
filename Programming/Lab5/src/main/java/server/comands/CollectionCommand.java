package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

import java.util.Objects;

public abstract class CollectionCommand extends Command{
    private final CollectionManager collectionManager;

    public CollectionCommand(CollectionManager collectionManager, String name, String description, Argument[] usage){
        super(name, description, usage);
        this.collectionManager = collectionManager;
    }

    public abstract Responce execute(Request r);

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), collectionManager);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || o.getClass() != this.getClass()) { return false; }
        CollectionCommand command = (CollectionCommand) o;
        return Objects.equals(this, command) &&
                Objects.equals(this.collectionManager, command.getCollectionManager());
    }
}
