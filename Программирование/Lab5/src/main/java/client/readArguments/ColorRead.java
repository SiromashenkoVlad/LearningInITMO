package client.readArguments;

import client.Console.Console;
import client.Interrogator;
import common.Enums.Color;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColorRead {
    public Color read(Console console, Interrogator interrogator){
        String ask;
        List<String> colors = Arrays.stream(Color.values())
                .map(Enum::name) // или .map(s -> s.name())
                .collect(Collectors.toList());;
        for (int i = 0; i < 5; ++i){
            console.println("Введите цвет ваших глаз, используя представленные варианты:");
            console.println(colors);
            ask = interrogator.getUserScanner().next();
            if (colors.get(ask))
        }
    }
}
