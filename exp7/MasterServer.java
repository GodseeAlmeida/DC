import java.io.*;
import java.net.*;
import java.util.*;

public class MasterServer {
    private Map<String, List<String>> fileSystem;

    public MasterServer() {
        fileSystem = new HashMap<>();
    }

    public void addFile(String fileName, String owner) {
        fileSystem.put(fileName, new ArrayList<>(Collections.singletonList(owner)));
    }

    public List<String> getFileOwners(String fileName) {
        return fileSystem.get(fileName);
    }

    public synchronized boolean writeFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        MasterServer masterServer = new MasterServer();
        // Start master server
        System.out.println("Master server started.");
    }
}
