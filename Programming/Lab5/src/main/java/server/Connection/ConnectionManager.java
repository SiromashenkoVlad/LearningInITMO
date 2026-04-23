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
        sc.configureBlocking(false);
        sc.register(key.selector(), OP_READ, new Attach(10000));
    }

    public void doRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        Attach attach = (Attach) key.attachment();
        ByteBuffer bf = attach.getBf();
        int byteread = sc.read(bf);
        if (byteread == -1){
            sc.close();
            return;
        }


        if (attach.getLength() == 0){
            if (bf.position() >= 4){
                bf.flip();
                int length = bf.getInt();
                System.out.println("Дошел размер " + length);
                bf.compact();
                attach.setLength(length);
            } else {
                return;
            }
        }

        if (attach.getLength() <= bf.position()){
            bf.flip();
            System.out.println(attach.getBf().capacity());
            byte[] bytesFilename = new byte[attach.getLength()];
            bf.get(bytesFilename);
            if (attach.isFirstTime()){
                String filename = byteBufferToObject(ByteBuffer.wrap(bytesFilename));
                filename = filename.trim();
                System.out.println("Имя файла получено: [" + filename + "]");

                Communicator comm = new Communicator(filename);
                attach.setCommunicator(comm);
                sendingResponse((Serializable) attach.getCommunicator().getUsagesCommands(), key);
                attach.setFirstTime();
                System.out.println("Отправил usages");
            } else {
                Request req = byteBufferToObject(ByteBuffer.wrap(bytesFilename));
                System.out.println("req получен");
                Responce rep = attach.getCommunicator().call(req);
                System.out.println("rep получен");
                sendingResponse(rep, key);
            }
            bf.clear();
            attach.setLength(0);
        }
    }

    public <T> T byteBufferToObject(ByteBuffer bf) throws IOException{
        byte[] objBytes = new byte[bf.remaining()];
        bf.get(objBytes);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Класс не найден при десериализации", e);
        }
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