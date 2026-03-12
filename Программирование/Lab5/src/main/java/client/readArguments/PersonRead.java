package client.readArguments;

import client.Console.Console;
import client.Interrogator;
import common.Enums.Color;
import common.Enums.Country;
import common.Mainpart.Person;
import common.Model.Coordinates;
import common.Model.Location;

import java.time.LocalDateTime;

public class PersonRead extends ValueReader implements Reader{
    public Person read(Console console, Interrogator interrogator){
        String name = this.read(console, interrogator, () -> interrogator.getUserScanner().next(),
                null, "Введите имя", "");
        Coordinates coordinates = (new CoordinateRead()).read(console, interrogator);
        Long height = this.read(console, interrogator, () -> interrogator.getUserScanner().nextLong(),
                value -> value > 0, "Введите высоту(она должна быть > 0)", "Ошибка ввода высоты");
        LocalDateTime birthday = this.read(console, interrogator,
                () -> LocalDateTime.parse(interrogator.getUserScanner().next()), value -> value != null,
                "Введите дату рождения. Пример ввода 2007-12-03T10:15:30", "Ошибка ввода");
        Color eyeColor = (new ColorRead()).read(console, interrogator);
        Country country = (new CountryRead()).read(console, interrogator);
        Location location = (new LocationRead()).read(console, interrogator);
        return new Person(name, coordinates, height, birthday, eyeColor, country, location);
    }
}
