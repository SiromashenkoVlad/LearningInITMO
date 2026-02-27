package server.managers;

import common.Enums.Color;
import common.Enums.Country;
import common.Mainpart.Person;
import common.Model.Coordinates;
import common.Model.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DumpManager {
    public static List<Person> read(String fileName) {
        List<Person> persons = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                StandardCharsets.UTF_8));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setTrim(true).build())) {
            for (CSVRecord record : parser) {
                try{
                    int id = Integer.parseInt(record.get(0));
                    String name = record.get(1);
                    Coordinates coordinates = new Coordinates(Float.parseFloat(record.get(2)),
                            Float.parseFloat(record.get(3)));
                    ZonedDateTime creationDate = ZonedDateTime.parse(record.get(4));
                    long height = Long.parseLong(record.get(5));
                    LocalDateTime birthday = LocalDateTime.parse(record.get(6));
                    Color color = Color.valueOf(record.get(7));
                    Country nationality = Country.valueOf(record.get(8));
                    Location location = new Location(Integer.parseInt(record.get(9)),
                            Integer.parseInt(record.get(10)),
                            Integer.parseInt(record.get(11)));

                    Person person = new Person(id, name, coordinates, creationDate, height,
                            birthday, color, nationality, location);
                    persons.add(person);
                } catch (Exception e) {
                    System.out.println("Ошибка чтения строки: " + record.toString());
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return persons;
    }

    public static void write(String fileName, List<Person> persons) {

        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Person person : persons) {
                printer.printRecord(
                        person.getId(),
                        person.getName(),
                        person.getCoordinates().getX(),
                        person.getCoordinates().getY(),
                        person.getCreationDate(),
                        person.getHeight(),
                        person.getBirthday(),
                        person.getEyeColor(),
                        person.getNationality(),
                        person.getLocation().getX(),
                        person.getLocation().getY(),
                        person.getLocation().getZ()
                );
            }
            printer.flush();

        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}