package exercise1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import utils.CustomLogger;

public class ThreadExercise {

    public static void main(String[] args) {
        // Create 10 threads and start them
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new CustomRunnable(), "Thread-" + i);
            thread.start();
        }
    }
}

class CustomRunnable implements Runnable {
    // The creation time is final because it should not be changed after the thread is created
    private final String creationTime;
    // Add a logger to log the creation time of the thread
    private static final Logger logger = CustomLogger.getLogger(CustomRunnable.class);

    public CustomRunnable() {
        creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    @Override
    public void run() {
        // Log the creation time of the thread
        logger.info(Thread.currentThread().getName() + " created at " + creationTime);
    }
}
