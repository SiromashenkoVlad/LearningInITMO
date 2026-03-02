package server.comands;

import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class InfoCommand extends Command implements WithoutArguments {
    CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager){
        super("info", "выводит в стандартный поток вывода информацию о коллекции" +
                " (тип, дата инициализации, количество элементов)", "");
        this.collectionManager = collectionManager;
    }

    @Override
    public Responce execute(Request r) {
        try{
            StringBuilder answer = new StringBuilder();

            answer.append(collectionManager.toString());
            answer.append("\n Дата инициализации: ")
                    .append(collectionManager.getLastInitTime());
            answer.append("\n Дата сохранения: ")
                    .append(collectionManager.getLastSaveTime());

            return new Responce(true, answer.toString());
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды info");
        }
    }
}
