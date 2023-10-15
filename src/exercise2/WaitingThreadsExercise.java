package exercise2;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class WaitingThreadsExercise {

    public static void main(String[] args) {
        // Create 3 threads and start them
        Thread thread1 = new Thread(new WaitingRunnable(500), "Thread-1");
        Thread thread2 = new Thread(new WaitingRunnable(1500), "Thread-2");
        Thread thread3 = new Thread(new WaitingRunnable(10000), "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class WaitingRunnable implements Runnable {
    // The wait time and creation time are final because they should not be changed after the thread is created
    private final long waitTime;
    private final String creationTime;
    private static final Logger logger = Logger.getLogger(WaitingRunnable.class.getName());

    public WaitingRunnable(long waitTime) {
        this.waitTime = waitTime;
        creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

    }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getName() + " is starting to wait.");
            Thread.sleep(waitTime);
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            logger.info(Thread.currentThread().getName() + " started waiting at " + creationTime + " and finished waiting at " + currentTime);
        } catch (InterruptedException e) {
            // Catch the exception and log it, it's safe to ignore it
            logger.info(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}
