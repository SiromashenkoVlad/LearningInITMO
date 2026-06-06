package server.сonnection;

import common.requests.Request;
import common.requests.Responce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.managers.WorkManager;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;


public class ClientHandler implements Runnable{
    private final Socket client;
    private final ForkJoinPool pool;
    private final static Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private final WorkManager workManager = WorkManager.getInstance();


    public ClientHandler(Socket socket, ForkJoinPool forkJoinPool) {
        this.client = socket;
        this.pool = forkJoinPool;
    }

    @Override
    public void run() {
        auth();

        try {
            while (true) {
                byte[] sizebt = new byte[4];
                InputStream is = client.getInputStream();
                is.read(sizebt);
                int size = java.nio.ByteBuffer.wrap(sizebt).getInt();
                byte[] byteObject = new byte[size];
                is.read(byteObject);
                Request r = byteBufferToObject(ByteBuffer.wrap(byteObject));
                Optional<Responce> rep = pool.invoke(new Task(r));
                new Thread(() -> sendingResponse(rep.get())).start();
            }
        } catch (IOException e){
            LOGGER.error(e);
        }
    }


    private void auth(){
        LOGGER.info("Клиент подключился: " + client.getLocalAddress());
        new Thread(() -> sendingResponse((Serializable) workManager.getUsagesCommands())).start();
        LOGGER.info("Usage отправил");
    }

    public byte[] objectToByteArray(Serializable data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(data);
        oos.flush();
        return baos.toByteArray();
    }

    public void sendingResponse(Serializable data){
        synchronized (client) {
            try {
                OutputStream os = client.getOutputStream();
                byte[] object = objectToByteArray(data);
                ByteBuffer sizebf = ByteBuffer.allocate(4);
                sizebf.putInt(object.length);
                os.write(sizebf.array());
                os.flush();
                os.write(object);
                os.flush();
            } catch (IOException e) {
                LOGGER.error("Ошибка отправления запроса " + e.getMessage());
            }
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
}
