package server.сonnection;

import common.requests.Request;
import common.requests.Responce;
import server.managers.WorkManager;

import java.util.Optional;
import java.util.concurrent.RecursiveTask;

public class Task extends RecursiveTask<Optional<Responce>> {
    private WorkManager workManager = WorkManager.getInstance();
    private Request r;

    public Task(Request r){
        this.r = r;
    }

    protected Optional<Responce> compute(){
        if (r == null){
            System.out.println("В объекте Task нет запроса на выполнение");
            return Optional.empty();
        }
        return Optional.of(workManager.callCommand(r));
    }
}
