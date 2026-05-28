package client;

import common.exceptions.DisconnectFromServer;
import common.requests.Request;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ConnectionManager implements Closeable {
    private SocketChannel clientChannel;
    private ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
    private Request lastRequest;

    public ConnectionManager(InetAddress host, int port) throws IOException {
        this.clientChannel = SocketChannel.open(new InetSocketAddress(host, port));
        this.clientChannel.configureBlocking(true);
        System.out.println("Подключились к серверу через SocketChannel");
    }

    public void sendingRequest(Serializable r) throws IOException {
        if (r instanceof Request){
            lastRequest = (Request) r;
        }
        byte[] objectBytes = objectToByteArray(r);

        ByteBuffer buffer = ByteBuffer.allocate(4 + objectBytes.length);
        buffer.putInt(objectBytes.length);
        buffer.put(objectBytes);
        buffer.flip();

        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
        System.out.println("Запрос отправлен, байт: " + objectBytes.length);
    }

    public Serializable gettingResponse() throws IOException, ClassNotFoundException {
        sizeBuffer.clear();
        while (sizeBuffer.hasRemaining()){
            int bytesRead = clientChannel.read(sizeBuffer);
            if (bytesRead == -1) throw new DisconnectFromServer("Сервер закрыл соединение", lastRequest);
        }

        sizeBuffer.flip();
        int size = sizeBuffer.getInt();
        System.out.println(size);
        ByteBuffer objectBuffer = ByteBuffer.allocate(size);

        while (objectBuffer.hasRemaining()){
            int bytesRead = clientChannel.read(objectBuffer);
            if (bytesRead == -1) throw new DisconnectFromServer("Сервер закрыл соединение", lastRequest);
        }

        try {
            return bufferToObject(objectBuffer);
        } catch (IOException e) {
            throw new IOException("Ошибка в bufferToObject", e);
        }
    }



    private byte[] objectToByteArray(Serializable data) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(data);
            oos.flush();
            return baos.toByteArray();
        }
    }

    private Serializable bufferToObject(ByteBuffer bf) throws IOException, ClassNotFoundException {
        bf.flip();
        byte[] data = new byte[bf.remaining()];
        bf.get(data);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Serializable) ois.readObject();
        }
    }

    @Override
    public void close() throws IOException {
        if (clientChannel != null) clientChannel.close();
    }
}