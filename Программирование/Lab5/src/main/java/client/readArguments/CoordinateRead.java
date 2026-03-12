package client.readArguments;

import client.Console.Console;
import client.Interrogator;
import common.Model.Coordinates;


public class CoordinateRead extends ValueReader implements Reader{
    public Coordinates read(Console console, Interrogator interrogator){
        console.println("Начался ввод координаты. Первым считывается значение x, вторым y");
        float x = this.read(console, interrogator, () -> interrogator.getUserScanner().nextFloat(),
                value -> value <= 951, "Введите нецелочисленное значение(числа с запятой) <= 951",
                "x должен быть <= 951");

        float y = this.read(console, interrogator, () -> interrogator.getUserScanner().nextFloat(),
                value -> value > -733, "Введите нецелочисленное значение(числа с запятой) > -733",
                "y должен быть > -733");

        return new Coordinates(x, y);
    }
}
