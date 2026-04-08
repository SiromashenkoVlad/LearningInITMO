package common.model;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private static int nextId = 1;

    private final float x; //Максимальное значение поля: 951
    private final float y; //Значение поля должно быть больше -733

    public Coordinates(float xcoord, float ycoord){
        if (xcoord > 951) {
            throw new IllegalArgumentException("x должен быть <= 951");
        }
        if (ycoord <= -733) {
            throw new IllegalArgumentException("y должен быть > -733");
        }
        x = xcoord;
        y = ycoord;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coord = (Coordinates) o;
        return Float.compare(this.x, coord.getX()) == 0 &&
                Float.compare(this.y, coord.getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "x coordinate: " + x + ", y coordinate: " + y;
    }
}
