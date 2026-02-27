package server.comands;

import common.Enums.Commands;
import common.Mainpart.Person;
import server.managers.CollectionManager;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class FieldDescendingEyeColorCommand extends Command{
    CollectionManager collectionManager;

    public FieldDescendingEyeColorCommand(CollectionManager collectionManager){
        super("print_field_descending_eye_color", "вывести значения поля eyeColor" +
                " всех элементов в порядке убывания", "");
        this.collectionManager = collectionManager;
    }

    public String execute() {
        return collectionManager.getCollection().stream()
                .map(Person::getEyeColor)          // берём только eyeColor
                .filter(Objects::nonNull)          // убираем null
                .sorted(Comparator.reverseOrder()) // сортировка по убыванию
                .map(Enum::name)                   // в строку
                .collect(Collectors.joining("\n"));
    }
}
