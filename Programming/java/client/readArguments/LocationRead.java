package client.readArguments;

import client.console.Console;
import client.Interrogator;
import common.model.Location;

import java.util.Objects;


public class LocationRead extends ValueReader implements Reader{
    public Location read(Console console, Interrogator interrogator){
        console.println("Начался ввод локации. Поочередно внести координаты x, y, z:");
        int x = this.read(console, interrogator, ()-> interrogator.getUserScanner().nextInt(),
                null, "Введите целочисленную координату x", "Ошибка ввода числа");
        Integer y = this.read(console, interrogator, ()->interrogator.getUserScanner().nextInt(),
                Objects::nonNull, "Введите целочисленную координату y", "Поле должно быть не null");
        int z = this.read(console, interrogator, ()-> interrogator.getUserScanner().nextInt(),
                null, "Введите целочисленную координату z", "Ошибка ввода числа");
        return new Location(x, y, z);
    }
}
