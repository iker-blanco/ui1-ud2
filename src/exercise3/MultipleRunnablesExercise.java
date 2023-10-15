package exercise3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import utils.CustomLogger;

public class MultipleRunnablesExercise {
    public static void main(String[] args) {
        CustomRunnable runnable1 = new CustomRunnable(5);
        CustomRunnable runnable2 = new CustomRunnable(10);
        CustomRunnable runnable3 = new CustomRunnable(15);

        runnable1.startThreads();
        runnable2.startThreads();
        runnable3.startThreads();
    }
}

class CustomRunnable {
    // CustomRunnable class that creates a specified number of threads and logs their creation time
    // The number of threads is final because it is set at the time of object creation
    private final int numOfThreads;

    private static final Logger logger = CustomLogger.getLogger(CustomRunnable.class);


    public CustomRunnable(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    public void startThreads() {
        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new Thread(() -> {
                String creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                logger.info(Thread.currentThread().getName() + " created at " + creationTime);
            }, "Thread-" + numOfThreads + "-" + i);
            thread.start();
        }
    }
}
