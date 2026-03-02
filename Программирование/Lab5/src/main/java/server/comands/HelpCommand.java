package server.comands;

import common.Intefaces.WithoutArguments;
import common.requests.Request;
import common.requests.Responce;
import server.managers.RequestHandler;

public class HelpCommand extends Command implements WithoutArguments {
    RequestHandler collection;
    public HelpCommand(RequestHandler collectionManager){
        super("help", "выводит справку по доступным командам", "");
        collection = collectionManager;
    }

    @Override
    public Responce execute(Request r){
        try{
            StringBuilder answer = new StringBuilder();
            for (Command command : collection.getCommands().values()){
                answer.append(command.getName()).append(" ").append(command.getDescription());
            }
            return new Responce(true, answer.toString());
        } catch (Exception e){
            return new Responce(false, "Ошибка выполнения команды help");
        }
    }
}
