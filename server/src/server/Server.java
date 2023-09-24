package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import product.Product;

public class Server {
    public static void Init() {
        try (ServerSocket ss = new ServerSocket(5000)) {
            System.out.println("Successfully initiated Server.\nWaiting for Client...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("Connected to Client!");
                Response response = new Response();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Object object = ois.readObject();
                response = Product.Routes(object);
                s.isClosed();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(response);

                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
