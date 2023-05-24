package learn.ThreadHandle;

class ThreadStatus extends Thread {

    boolean waiting = true;
    boolean ready = false;

    ThreadStatus() {
    }

    @Override
    public void run() {
        String tName = Thread.currentThread().getName();
        System.out.println(tName + " starting.");
        if (waiting) {
            System.out.println("waiting:" + waiting);
        }
        System.out.println("waiting...");
        startWait();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException exc) {
            System.out.println(tName + " interrupted.");
        }
        System.out.println(tName + " terminating.");
    }

    synchronized void startWait() {
        try {
            if (!ready) {
                wait();
            }
        } catch (InterruptedException exc) {
            System.out.println("wait() interrupted");
        }
    }

    synchronized void notice() {
        ready = true;
        notify();
    }

    public static void main(String args[]) throws Exception {
        ThreadStatus t = new ThreadStatus();
        t.setName("MyThread #1");
        showThreadStatus(t);
        t.start();
        showThreadStatus(t);
        t.waiting = false;
        Thread.sleep(50);
        showThreadStatus(t);
        t.notice();
        showThreadStatus(t);
        showThreadStatus(t);
    }

    static void showThreadStatus(Thread t) {
        System.out.println(t.getName() + " Alive:" + t.isAlive() + " State:" + t.getState());
    }
}

/*
MyThread #1 Alive:false State:NEW
MyThread #1 Alive:true State:RUNNABLE
MyThread #1 starting.
waiting...
MyThread #1 Alive:true State:WAITING
MyThread #1 Alive:true State:BLOCKED
MyThread #1 Alive:true State:TIMED_WAITING
MyThread #1 terminating.
BUILD SUCCESSFUL (total time: 1 second)
*/