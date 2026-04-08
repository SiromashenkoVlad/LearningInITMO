package server.managers;

import common.requests.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager implements Closeable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ConnectionManager(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту " + port);
    }

    public void acceptClient() throws IOException {
        if (clientSocket != null) clientSocket.close();

        this.clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился: " + clientSocket.getInetAddress());

        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public Request gettingRequest() throws IOException, ClassNotFoundException {
        return (Request) inputStream.readObject();
    }

    public String gettingFile() throws IOException, ClassNotFoundException {
        return (String) inputStream.readObject();
    }

    public void sendingResponse(Serializable data) throws IOException {
        outputStream.writeObject(data);
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        if (clientSocket != null) clientSocket.close();
        if (serverSocket != null) serverSocket.close();
    }
}