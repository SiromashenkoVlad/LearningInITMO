package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Command {
    private final String name;
    private final String description;
    private final Argument[] usage;

    public Command(String name, String description, Argument[] usage){
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public abstract Responce execute(Request r);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Argument[] getUsage() {
        return usage;
    }

    public String getNameArgumentByIndex(int id){
        return usage[id].getName();
    }

    public String usageString(){
        return Arrays.stream(usage).map(Argument::getName).collect(Collectors.joining(" "));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, usage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || o.getClass() != this.getClass()) { return false; }
        CollectionCommand command = (CollectionCommand) o;
        return Objects.equals(this.name, command.getName()) &&
                Objects.equals( this.description, command.getDescription());
    }
}
