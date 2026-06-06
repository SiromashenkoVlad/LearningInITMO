package server.comands;

import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import server.managers.CollectionManager;

public class InfoCollectionCommand extends CollectionCommand {
    public InfoCollectionCommand(CollectionManager collectionManager){
        super(collectionManager, "info", "выводит в стандартный поток вывода информацию о коллекции" +
                " (тип, дата инициализации, количество элементов)", new Argument[0]);
    }

    @Override
    public Responce execute(Request r) {
        try{
            StringBuilder answer = new StringBuilder();
            CollectionManager collectionManager = this.getCollectionManager();
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
