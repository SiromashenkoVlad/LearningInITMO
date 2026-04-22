package server.Connection;

import common.requests.Request;
import common.requests.Responce;
import server.managers.Communicator;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_READ;

public class ConnectionManager implements Closeable {
    private Selector selector;
    private ServerSocketChannel channel;

    public ConnectionManager(int port) throws IOException {
        channel = ServerSocketChannel.open();
        selector = Selector.open();
        channel.socket().bind(new InetSocketAddress(port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Сервер запущен на порту " + port);
    }


    public void execute(){
        try{
            while(true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (var iter = keys.iterator(); iter.hasNext(); ) {
                    SelectionKey key = iter.next(); iter.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) { doAccept(key); }
                        if (key.isReadable()) { doRead(key); }
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Selector буянит " + e.getMessage());
        }
    }

    public void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        System.out.println("Клиент подключился: " + sc.getLocalAddress());

        ByteBuffer byteBufferWithFilename = ByteBuffer.allocate(10000);
        sc.read(byteBufferWithFilename);
        String filename = new String(byteBufferWithFilename.array());

        key.attach(new Communicator(filename));

        Communicator comm = (Communicator) key.attachment();
        sendingResponse((Serializable) comm.getUsagesCommands(), key);
        sc.configureBlocking(false);
        sc.register(key.selector(), OP_READ);
    }

    public void doRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        Request req = gettingData(sc);
        Communicator comm = (Communicator) key.attachment();
        Responce resp = comm.call(req);
        sendingResponse(resp, key);
    }

    public <T> T ByteBufferToObject(ByteBuffer bf) throws IOException{
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(bf.array());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public <T> T gettingData(SocketChannel sc) throws IOException {
        ByteBuffer bf = ByteBuffer.allocate(10000);
        sc.read(bf);
        return ByteBufferToObject(bf);
    }

    public byte[] objectToByteArray(Serializable data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(data);
        oos.flush();
        return baos.toByteArray();
    }

    public void sendingResponse(Serializable data, SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        byte[] bt = objectToByteArray(data);
        sc.write(ByteBuffer.wrap(bt));
    }

    @Override
    public void close() throws IOException {
        if (selector != null) selector.close();
        if (channel != null) channel.close();
    }
}