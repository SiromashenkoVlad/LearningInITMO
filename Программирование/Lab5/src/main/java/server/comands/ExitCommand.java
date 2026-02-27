package server.comands;

public class ExitCommand extends Command{
    public ExitCommand(){
        super("exit", "завершить программу (без сохранения в файл)", "");
    }

    public String execute(){
        return "Свобода";
    }
}
