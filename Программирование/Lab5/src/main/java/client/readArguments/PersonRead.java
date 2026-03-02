package client.readArguments;

import client.Console.Console;
import client.Interrogator;
import common.Intefaces.OnesStringArgumentable;
import common.Mainpart.Person;
import common.Model.Coordinates;

public class PersonRead {
    public Person read(Console console, Interrogator interrogator){
        int id; String name;
        Coordinates coordinates;
        int height;

        if (availableCommands.get(userCommand) instanceof OnesStringArgumentable) {
            console.println("Начался ввод");
            for (int i = 0; i < 5; ++i){
                console.println("Введите id: ");
            }

        }

    }

    public

}
