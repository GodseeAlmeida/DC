import java.util.ArrayList;
import java.util.List;

// Process class representing each process in the system
class Process {
    private int id;
    private boolean coordinator;
    private boolean active;

    public Process(int id) {
        this.id = id;
        this.coordinator = false;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public boolean isCoordinator() {
        return coordinator;
    }

    public void setCoordinator(boolean coordinator) {
        this.coordinator = coordinator;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

// CoordinatorElection class implementing the Bully Election Algorithm
public class CoordinatorElection {
    private List<Process> processes;

    public CoordinatorElection(int numProcesses) {
        processes = new ArrayList<>();
        for (int i = 0; i < numProcesses; i++) {
            processes.add(new Process(i));
        }
    }

    // Method to start the election process
    public void startElection() {
        for (Process process : processes) {
            if (process.isActive()) {
                for (Process p : processes) {
                    if (p.isActive() && p.getId() > process.getId()) {
                        // Send election message to higher priority processes
                        System.out.println("Process " + process.getId() + " sends election message to Process " + p.getId());
                    }
                }
            }
        }
    }

    // Method to handle the received election message
    public void handleElectionMessage(int senderId) {
        System.out.println("Received election message from Process " + senderId);
        // Elect self as coordinator and send victory message to lower priority processes
        for (Process process : processes) {
            if (process.isActive() && process.getId() < senderId) {
                System.out.println("Process " + process.getId() + " receives victory message from Process " + senderId);
            }
        }
    }

    // Method to elect a coordinator
    public void electCoordinator() {
        for (Process process : processes) {
            if (process.isActive()) {
                process.setCoordinator(true);
                System.out.println("Process " + process.getId() + " is elected as coordinator.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        CoordinatorElection coordinatorElection = new CoordinatorElection(5); // Number of processes in the system
        coordinatorElection.startElection();
        // Assume some process has detected the failure of higher priority processes and initiated the election
        coordinatorElection.handleElectionMessage(3); // Example, process 3 detects failure and initiates election
        coordinatorElection.electCoordinator(); // Elect a coordinator
    }
}
