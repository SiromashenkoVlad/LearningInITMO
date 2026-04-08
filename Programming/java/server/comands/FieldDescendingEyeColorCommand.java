package server.comands;

import common.mainpart.Person;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class FieldDescendingEyeColorCommand extends CommandCollection {
    public FieldDescendingEyeColorCommand(CollectionManager collectionManager){
        super(collectionManager,"print_field_descending_eye_color", "вывести значения поля eyeColor" +
                " всех элементов в порядке убывания", new Argument[0]);
    }

    @Override
    public Responce execute(Request r) {
        try{
            return new Responce(true, this.getCollectionManager().getCollection().stream()
                    .map(Person::getEyeColor)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .map(Enum::name)
                    .collect(Collectors.joining("\n")));
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды print_field_descending_eye_color");
        }
    }
}
