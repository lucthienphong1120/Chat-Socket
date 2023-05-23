package learn;

public class MultiThread extends Thread { // default in java.lang.Thread

    String message;

    MultiThread(String name, String message) {
        this.setName(name);
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("Thread " + this.getName() + " with message: " + message);
    }

    public static void main(String[] args) {
        String message = "hello";
        int pri[] = {
            Thread.MAX_PRIORITY,
            Thread.NORM_PRIORITY,
            Thread.MIN_PRIORITY
        };
        for (int i = 0; i < 4; i++) {
            MultiThread t = new MultiThread("T" + i, message);
            t.setPriority(pri[i % 3]); // set priority for thread
            t.start();
        }
    }
}
