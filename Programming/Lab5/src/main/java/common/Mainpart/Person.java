package common.Mainpart;

import common.enums.Color;
import common.enums.Country;
import common.model.Coordinates;
import common.model.Location;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;


public class Person implements Comparable<Person>, Serializable {
    private static final long serialVersionUID = 3L;
    private static int nextId = 1;

    private final int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long height; //Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private Color eyeColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null

    public Person(String name, Coordinates coordinates, long height,
                  LocalDateTime birthday, Color eyeColor, Country nationality, Location location){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть равно null или быть пустым");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Поле coordinates не может быть равно null");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Поле height не может быть меньше или равно 0");
        }
        if (birthday == null) {
            throw new IllegalArgumentException("Поле birthday не может быть равно null");
        }
        if (eyeColor == null) {
            throw new IllegalArgumentException("Поле eyecolor не может быть равно null");
        }
        this.id = nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.height = height;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person(int id, String name, Coordinates coordinates, ZonedDateTime creationDate,
                  long height, LocalDateTime birthday, Color eyeColor, Country nationality, Location location) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть равно null или быть пустым");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Поле coordinates не может быть равно null");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Поле height не может быть меньше или равно 0");
        }
        if (birthday == null) {
            throw new IllegalArgumentException("Поле birthday не может быть равно null");
        }
        if (eyeColor == null) {
            throw new IllegalArgumentException("Поле eyecolor не может быть равно null");
        }
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate не может быть null");
        }

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.location = location;

        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public static void setNextId(int nextId) {
        Person.nextId = nextId;
    }

    public static int getNextId() {
        return nextId;
    }

    public void update(Person p){
        this.name = p.name;
        this.coordinates = p.coordinates;
        this.creationDate = p.creationDate;
        this.height = p.height;
        this.birthday = p.birthday;
        this.eyeColor = p.eyeColor;
        this.nationality = p.nationality;
        this.location = p.location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate(){
        return creationDate;
    }

    public long getHeight() {
        return height;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int compareTo(Person p){
        try{
            return objectToByteArray(this).length - objectToByteArray(p).length;
        } catch (IOException e) {
            return 0;
        }
    }

    public byte[] objectToByteArray(Serializable data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(data);
        oos.flush();
        return baos.toByteArray();
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, name, coordinates, creationDate, height,
                birthday, eyeColor, nationality, location);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) { return true; }
        if (o == null || this.getClass() != o.getClass()) { return false; }
        Person p = (Person) o;
        return id == p.id;
    }

    @Override
    public String toString() {
        String info = "Person, id " + id;
        info += "\n name: " + name;
        info += "\n coordinates: " + coordinates;
        info += "\n creationDate: " + creationDate;
        info += "\n height: " + height;
        info += "\n birthday: " + birthday;
        info += "\n eyecolor: " + eyeColor;
        info += "\n nationality: " + nationality;
        info += "\n location: " + location;
        return info;
    }
}

