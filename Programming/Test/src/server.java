import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket sock; ServerSocket serv;
        OutputStream os; InputStream is;
        InetAddress host = InetAddress.getLocalHost(); int port = 3418;
        serv = new ServerSocket(port);
        sock = serv.accept();

        is = sock.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(is);
        Request r = (Request) objectInputStream.readObject();

        r.getArgs().put("{element}", new Person("Dmitriy", "Gridasov"));

        os = sock.getOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(r);
        outputStream.flush();
    }
}
