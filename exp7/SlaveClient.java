import java.io.*;
import java.net.*;

public class SlaveClient {
    private String masterAddress;
    private int masterPort;

    public SlaveClient(String masterAddress, int masterPort) {
        this.masterAddress = masterAddress;
        this.masterPort = masterPort;
    }

    public void readFile(String fileName) {
        try (Socket socket = new Socket(masterAddress, masterPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send read request to master
            out.println("READ " + fileName);

            // Receive file content from master
            String response = in.readLine();
            System.out.println("File content: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String fileName, String content) {
        try (Socket socket = new Socket(masterAddress, masterPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send write request to master
            out.println("WRITE " + fileName + " " + content);

            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SlaveClient client = new SlaveClient("localhost", 3000); // Provide master's address and port
        client.readFile("example.txt");
        client.writeFile("example.txt", "New content");
    }
}
