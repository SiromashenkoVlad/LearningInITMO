package server.comands;

import common.Enums.Commands;
import server.managers.CollectionManager;

public class InfoCommand extends Command{
    CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager){
        super("info", "выводит в стандартный поток вывода информацию о коллекции" +
                " (тип, дата инициализации, количество элементов)", "");
        this.collectionManager = collectionManager;
    }

    public String execute() {
        StringBuilder answer = new StringBuilder();

        answer.append(collectionManager.toString());
        answer.append("\n Дата инициализации: ")
                .append(collectionManager.getLastInitTime());
        answer.append("\n Дата сохранения: ")
                .append(collectionManager.getLastSaveTime());

        return answer.toString();
    }
}
