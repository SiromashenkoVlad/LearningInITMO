package client.readArguments;

import client.Console.Console;
import client.Interrogator;

public class IdReader extends ValueReader implements Reader{
    public Integer read(Console console, Interrogator interrogator){
        return this.read(console, interrogator, () -> interrogator.getUserScanner().nextInt(),
                value -> value > 0, "Введите id > 0", "Это точно было не id");
    }
}
