package common.model;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Comparable<Location>, Serializable {
    private static int nextId = 1;

    private final int x;
    private final Integer y; //Поле не может быть null
    private final int z;

    public Location(int xcoord, Integer ycoord, int zcoord){
        if (ycoord == null) {
            throw new IllegalArgumentException("y должен быть не null");
        }
        x = xcoord;
        y = ycoord;
        z = zcoord;
    }

    public int getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public int getZ() { return z; }

    @Override
    public int compareTo(Location l){
        float thisValue = x * x + y * y + z * z;
        float otherValue = l.getX() * l.getX()
                + l.getY() * l.getY()
                + l.getZ() * l.getZ();

        return Float.compare(thisValue, otherValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y.equals(location.y) && z == location.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "x координата: " + x + ", y координата: " + y + ", z координата: " + z;
    }
}
