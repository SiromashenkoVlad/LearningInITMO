package client.readArguments;

import client.console.Console;
import client.Interrogator;
import common.enums.Country;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CountryRead extends ValueReader implements Reader{
    public Country read(Console console, Interrogator interrogator){
        return this.read(console, interrogator, () -> Country.valueOf(interrogator.getUserScanner().next()),
                null, "Введите страну из предложенного списка: "
                        + Arrays.stream(Country.values()).map(Enum::name).toList(),
                "Страна введена некорректно");
    }
}
