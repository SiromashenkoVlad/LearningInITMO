package common.requests;

public class Argument {
    private final String name;
    private final Class<?> type;

    public Argument(String name, Class<?> type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " " + type.toString();
    }
}
