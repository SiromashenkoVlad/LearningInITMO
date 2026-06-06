package client.readArguments;

import client.console.Console;
import client.Interrogator;
import common.exceptions.IncorrectDataInput;

import java.util.function.Predicate;
import java.util.function.Supplier;

class ValueReader {
    <T> T read(Console console, Interrogator interrogator,
            Supplier<T> supplier, Predicate<T> condition, String whatNeed, String errorMessage) {
        for (int i = 0; i < 10; i++) {
            try {
                console.println(whatNeed);
                T value = supplier.get();
                if (condition == null || condition.test(value)) {
                    return value;
                }
                console.println("Осталось попыток: " + (10 - i - 1));
            } catch (Exception e) {
                console.println(errorMessage + ". Некорректный ввод. Осталось попыток: "
                        + (10 - i - 1));
                interrogator.getUserScanner().nextLine();
            }
        }
        throw new IncorrectDataInput("Превышено число попыток ввода");
    }
}
