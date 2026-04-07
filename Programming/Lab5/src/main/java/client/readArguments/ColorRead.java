package client.readArguments;

import client.console.Console;
import client.Interrogator;
import common.enums.Color;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ColorRead extends ValueReader implements Reader{
    public Color read(Console console, Interrogator interrogator){
        return this.read(console, interrogator, () -> Color.valueOf(interrogator.getUserScanner().next()),
                null, "Введите цвет ваших глаз из списка: " +
                        Arrays.stream(Color.values()).map(Enum::name).toList(),
                "Цвет введен некорректно");
    }
}
