package server.Connection;

import common.mainpart.Person;
import common.requests.Request;
import common.requests.Responce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.managers.Communicator;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_READ;

public class ConnectionManager implements Closeable {
    private Selector selector;
    private ServerSocketChannel channel;
    private final static Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    public ConnectionManager(int port) throws IOException {
        channel = ServerSocketChannel.open();
        selector = Selector.open();
        channel.socket().bind(new InetSocketAddress("localhost", port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        LOGGER.info("Запустили сервер");
        System.out.println("Сервер запущен на порту " + port);
    }


    public void execute(){
        LOGGER.info("Старт основного цикла выполнения");
        while(true) {
            try{
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (var iter = keys.iterator(); iter.hasNext(); ) {
                    SelectionKey key = iter.next(); iter.remove();
                    if (!key.isValid()) { continue;}

                    if (key.isAcceptable()) {
                        try {
                            doAccept(key);
                        } catch (IOException e) {
                            LOGGER.warn("Не удалось принять соединение: {}", e.getMessage());
                            key.cancel();
                        }
                    }
                    if (key.isReadable()) {
                        try {
                            doRead(key);
                        } catch (IOException e) {
                            LOGGER.info("Клиент отключился: {}", e.getMessage());
                            handleClientDisconnect(key);
                        }
                    }
                }
            }catch (ClosedSelectorException e) {
                LOGGER.info("Селектор закрыт, завершаем работу сервера");
                break;
            } catch (IOException e) {
                LOGGER.error("Критическая ошибка селектора, завершаем сервер", e);
                break;
            }
        }
    }

    public void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        System.out.println("Клиент подключился: " + sc.getLocalAddress());
        LOGGER.info("Новое подключение клиента: {}", sc.getRemoteAddress());
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
                LOGGER.info("Пришел файл размера {}", length);
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
                LOGGER.info("Имя файла: {}", filename);


                Communicator comm = new Communicator(filename);
                attach.setCommunicator(comm);
                sendingResponse(Person.getNextId(), key);
                LOGGER.debug("Отправил ID Person на сервере: {}", Person.getNextId());
                sendingResponse((Serializable) attach.getCommunicator().getUsagesCommands(), key);
                attach.setFirstTime();
                System.out.println("Отправил usages");
                LOGGER.info("Usage отправил");
            } else {
                Request req = byteBufferToObject(ByteBuffer.wrap(bytesFilename));
                System.out.println("req получен");
                LOGGER.info("получил request {}", req);
                Responce rep = attach.getCommunicator().call(req);
                sendingResponse(rep, key);
                LOGGER.info("отправил response {}", rep);
            }
            bf.clear();
            attach.setLength(0);
        }
    }

    private void handleClientDisconnect(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            key.cancel();
            if (channel.isOpen()) {
                channel.close();
            }
            key.attach(null);

        } catch (IOException e) {
            LOGGER.warn("Ошибка при очистке соединения: {}", e.getMessage());
        }
    }

    public <T> T byteBufferToObject(ByteBuffer bf) throws IOException{
        byte[] objBytes = new byte[bf.remaining()];
        bf.get(objBytes);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Класс не найден при десериализации", e);
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
        LOGGER.info("Закрытие соединения");
    }
}