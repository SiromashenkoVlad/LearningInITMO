package common.requests;


import common.userData.CredentialsProvider;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Request implements Serializable {
    private static final long serialVersionUID = 4L;

    private final String name;
    private final Map<String, Object> args;
    private final CredentialsProvider credentialsProvider;

    public Request(String name, Map<String, Object> args, CredentialsProvider credentialsProvider){
        this.name = name;
        this.args = args;
        this.credentialsProvider = credentialsProvider;
    }

    public Request(String name, Map<String, Object> args){
        this.name = name;
        this.args = args;
        this.credentialsProvider = null;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public Object defineArgByName(String name){
        return args.get(name);
    }

    public CredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, args, credentialsProvider);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || this.getClass() != o.getClass()) { return false; }
        Request req = (Request) o;
        return this.name.equals(req.name);
    }
}
