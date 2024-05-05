import java.net.*;
import java.io.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter a number:");
            String userInput = reader.readLine();
            out.println(userInput);

            String response = in.readLine();
            System.out.println("Result is: " + response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
