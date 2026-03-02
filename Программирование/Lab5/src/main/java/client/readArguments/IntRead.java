package client.readArguments;

import client.Console.Console;
import client.Interrogator;
import common.Exceptions.IncorrectDataInput;
import common.Intefaces.Reader;

public class IntRead implements Reader {
    public Integer read(Console console, Interrogator interrogator){
        for (int i = 0; i < 10; ++i){
            console.println("Введите число");
            try{
                int dig = interrogator.getUserScanner().nextInt();
                return dig;
            } catch (Exception InputMismatchException){
                console.println("Ввод некорретен, у вас осталось " +(10 - i - 1) + "попыток");
            }
        }
        throw new IncorrectDataInput("Некорректная попытка ввода типа int");
    }
}
