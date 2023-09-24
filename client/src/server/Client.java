package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static Response response;

    private static String host = "localhost";
    private static Integer port = 5000;

    public static Socket newConnection() throws UnknownHostException, IOException {
        Socket Connection = new Socket(host, port);
        return Connection;
    }

    public static void sendRequest(Object reqObject) throws IOException, ClassNotFoundException {
        Socket Connection = newConnection();
        ObjectOutputStream oos = new ObjectOutputStream(Connection.getOutputStream());
        oos.writeObject(reqObject);
        ObjectInputStream ois = new ObjectInputStream(Connection.getInputStream());
        setResponse(ois.readObject());
    }

    public static Response getResponse() {
        return response;
    }

    public static void setResponse(Object object) {
        Client.response = (Response) object;
    }
}
