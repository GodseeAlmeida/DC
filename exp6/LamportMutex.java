import java.util.concurrent.atomic.AtomicInteger;

class LamportMutex {
    private static final int THREADS_COUNT = 3;
    private static final int NUM_CRITICAL_SECTIONS = 5;
    private static final int ITERATIONS_PER_SECTION = 100000;

    private static final AtomicInteger[] clocks = new AtomicInteger[THREADS_COUNT];
    private static volatile boolean[] entering = new boolean[THREADS_COUNT];

    static {
        for (int i = 0; i < THREADS_COUNT; i++) {
            clocks[i] = new AtomicInteger(0);
            entering[i] = false;
        }
    }

    static class Worker extends Thread {
        private final int id;

        Worker(int id) {
            this.id = id;
        }

        private int maxClock() {
            int max = -1;
            for (AtomicInteger clock : clocks) {
                max = Math.max(max, clock.get());
            }
            return max + 1;
        }

        private void requestCriticalSection() {
            entering[id] = true;
            clocks[id].set(maxClock());
            entering[id] = false;

            for (int other = 0; other < THREADS_COUNT; other++) {
                if (other != id) {
                    while (entering[other]) {
                        // Wait until the other thread finishes its request
                    }
                    while (clocks[other].get() != 0 && (clocks[id].get() > clocks[other].get()
                            || (clocks[id].get() == clocks[other].get() && id > other))) {
                        // Wait until it's my turn
                    }
                }
            }
        }

        private void releaseCriticalSection() {
            clocks[id].set(0);
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_CRITICAL_SECTIONS; i++) {
                for (int j = 0; j < ITERATIONS_PER_SECTION; j++) {
                    requestCriticalSection();
                    // Critical section
                    System.out.println("Thread " + id + " is in critical section " + i);
                    releaseCriticalSection();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Worker(i);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
