import java.io.*;
import java.net.*;

public class StatelessServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        System.out.println("Server started");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    String request = in.readLine();
                    System.out.println("Received request: " + request);
                    // Process request
                    out.println("Response to " + request);
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
