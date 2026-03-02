package client.readArguments;

import client.Console.Console;
import client.Interrogator;

public class StringRead {
    public String read(Console console, Interrogator interrogator){
        console.println("Введите поле типа строки: ");
        return interrogator.getUserScanner().next();
    }
}
