import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class ConnectionManager implements Closeable {

    private Selector selector;
    private ServerSocketChannel serverChannel;

    // Храним буферы клиентов
    private Map<SocketChannel, ByteBuffer> buffers = new HashMap<>();

    public ConnectionManager(int port) throws IOException {
        selector = Selector.open();

        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);

        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Сервер запущен на порту " + port);
    }

    public void eventLoop() throws IOException {
        while (true) {
            selector.select();

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isAcceptable()) {
                    accept();
                } if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void accept() throws IOException {
        ServerSocketChannel sock = buffers.
    }

    private void read(SelectionKey key) throws IOException {

    }

    private void send(SocketChannel client, Serializable obj) throws IOException {

    }

    private byte[] serialize(Serializable obj) throws IOException {

    }

    private Request desRequest(ByteBuffer buffer) {

    }

    @Override
    public void close() throws IOException {
        selector.close();
        serverChannel.close();
    }
}