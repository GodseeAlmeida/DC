import java.net.*;
import java.io.*;

public class Server {
    static class ProgramSkeleton extends Thread {
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(new ProgramHandler(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ProgramHandler implements Runnable {
        private Socket socket;

        public ProgramHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter writer = new PrintWriter(outputStream, true);

                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    int inputNumber = Integer.parseInt(inputLine);
                    int result = inputNumber * inputNumber;
                    writer.println(result);
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ProgramSkeleton skeleton = new ProgramSkeleton();
        skeleton.start();
    }
}
