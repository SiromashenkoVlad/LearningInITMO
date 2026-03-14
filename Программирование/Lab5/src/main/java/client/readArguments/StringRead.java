package client.readArguments;

import client.Console.Console;
import client.Interrogator;

public class StringRead extends ValueReader implements Reader{
    public String read(Console console, Interrogator interrogator){
        return this.read(console, interrogator, ()-> interrogator.getUserScanner().next(), null,
                "Введите требуюмую строку", "Ты как со строкой то не справился");
    }
}
