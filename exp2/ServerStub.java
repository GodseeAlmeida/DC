import java.io.*;
import java.net.*;
public class ServerStub {
    public static void main(String[] args) {
        try {
            RPCInterfaceImpl impl = new RPCInterfaceImpl();
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                int request = in.readInt();
                int result = impl.func(request);
                out.writeInt(result);
                out.flush();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
