package server.comands;

import java.util.Objects;

public abstract class Command {
    private final String name;
    private final String description;
    private final String usage;

    public Command(String name, String description, String usage){
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || o.getClass() != this.getClass()) { return false; }
        Command command = (Command) o;
        return Objects.equals(this.name, command.getName()) &&
                Objects.equals( this.description, command.getDescription());
    }
}
