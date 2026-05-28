package client.readArguments;

import client.console.Console;
import client.Interrogator;
import common.enums.Color;
import common.enums.Country;
import common.Mainpart.Person;
import common.model.Coordinates;
import common.model.Location;

import java.time.LocalDateTime;
import java.util.Objects;

public class PersonRead extends ValueReader implements Reader{
    public Person read(Console console, Interrogator interrogator){
        String name = this.read(console, interrogator, () -> interrogator.getUserScanner().next(),
                null, "Введите имя", "");
        Coordinates coordinates = (new CoordinateRead()).read(console, interrogator);
        Long height = this.read(console, interrogator, () -> interrogator.getUserScanner().nextLong(),
                value -> value > 0, "Введите высоту(она должна быть > 0)", "Ошибка ввода высоты");
        LocalDateTime birthday = this.read(console, interrogator,
                () -> LocalDateTime.parse(interrogator.getUserScanner().next()), Objects::nonNull,
                "Введите дату рождения. Пример ввода 2007-12-03T10:15:30", "Ошибка ввода");
        Color eyeColor = (new ColorRead()).read(console, interrogator);
        Country country = (new CountryRead()).read(console, interrogator);
        Location location = (new LocationRead()).read(console, interrogator);
        return new Person(name, coordinates, height, birthday, eyeColor, country, location);
    }
}
