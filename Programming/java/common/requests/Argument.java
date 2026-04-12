package common.requests;

import java.io.Serializable;

public class Argument implements Serializable {
    private static final long serialVersionUID = 1L;

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
