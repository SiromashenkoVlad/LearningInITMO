package client;

import common.requests.Responce;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionManager implements Closeable {
    private Socket serverSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ConnectionManager(InetAddress host, int port) throws IOException {
        this.serverSocket = new Socket(host, port);
        System.out.println("Подключились к серверу: " + serverSocket.getInetAddress());

        this.outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(serverSocket.getInputStream());
    }

    public void sendingRequest(Serializable r) throws IOException {
        outputStream.writeObject(r);
        outputStream.flush();
    }

    public Serializable gettingResponse() throws IOException, ClassNotFoundException {
        return (Serializable) inputStream.readObject();
    }

    @Override
    public void close() throws IOException {
        System.out.println("Закрытие ресурсов соединения...");
        if (inputStream != null) inputStream.close();
        if (outputStream != null) outputStream.close();
        if (serverSocket != null) serverSocket.close();
    }
}
