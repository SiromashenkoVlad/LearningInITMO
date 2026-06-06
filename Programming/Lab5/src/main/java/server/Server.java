package server;

import server.managers.CollectionManager;
import server.managers.WorkManager;
import server.сonnection.ClientHandler;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinPool;

public class Server implements Closeable {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public void start(int port) throws IOException, SQLException {
        WorkManager.getInstance(new CollectionManager());

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, forkJoinPool)).start();
            }
        }
    }

    @Override
    public void close(){
        forkJoinPool.shutdown();
    }
}
