package client;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ConnectionManager implements Closeable {
    private SocketChannel clientChannel;
    private final int MAX_BUFFER_SIZE = 65536;

    public ConnectionManager(InetAddress host, int port) throws IOException {
        this.clientChannel = SocketChannel.open(new InetSocketAddress(host, port));
        this.clientChannel.configureBlocking(true);
        System.out.println("Подключились к серверу через SocketChannel");
    }

    public void sendingRequest(Serializable r) throws IOException {
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
        ByteBuffer readBuffer = ByteBuffer.allocate(MAX_BUFFER_SIZE);
        int bytesRead = clientChannel.read(readBuffer);

        if (bytesRead == -1) throw new IOException("Сервер закрыл соединение");

        readBuffer.flip();
        return bufferToObject(readBuffer);
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