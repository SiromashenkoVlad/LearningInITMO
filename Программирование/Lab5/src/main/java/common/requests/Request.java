package common.requests;

import common.Mainpart.Person;
import common.Model.Location;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {
    private final String name;
    private final int id;
    private final Person person;
    private final Location location;
    private final String fileName;

    public Request(String name, int id, Person p, Location location, String fileName){
        this.name = name;
        this.id = id;
        this.person = p;
        this.location = location;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public Person getPerson() {
        return person;
    }

    public Location getLocation() {
        return location;
    }

    public String getFileName() {
        return fileName;
    }

    public int getId() {
        return id;
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
