package common.requests;

import common.Enums.Commands;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {
    private Commands name;

    public Request(Commands name){
        this.name = name;
    }

    public Commands getName() {
        return name;
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
