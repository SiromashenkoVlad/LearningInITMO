import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Map<String, Object> usages = new HashMap<>();
        usages.put("{element}", new Person("Vova", "Gridasov"));
        Request r = new Request("add", usages);


        Socket sock;
        OutputStream os; InputStream is;
        InetAddress host = InetAddress.getLocalHost(); int port;
        port = 3418;
        sock = new Socket(host,port);
        os = sock.getOutputStream();
        ObjectOutputStream outputStreamTCP = new ObjectOutputStream(os);
        outputStreamTCP.writeObject(r);
        outputStreamTCP.flush();


        is = sock.getInputStream();
        ObjectInputStream objectInputStreamTCP = new ObjectInputStream(is);
        Request answer = (Request) objectInputStreamTCP.readObject();

        System.out.println(answer.getArgs().values());
    }
}