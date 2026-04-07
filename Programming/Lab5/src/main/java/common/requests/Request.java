package common.requests;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Request implements Serializable {
    private final String name;
    private final Map<String, Object> args;

    public Request(String name, Map<String, Object> args){
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || this.getClass() != o.getClass()) { return false; }
        Request req = (Request) o;
        return this.name.equals(req.name);
    }
}
