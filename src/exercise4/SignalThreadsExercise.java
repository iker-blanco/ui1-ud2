package exercise4;


import java.util.concurrent.CountDownLatch;

import java.util.logging.Logger;

import utils.CustomLogger;

public class SignalThreadsExercise {

    public static void main(String[] args) {
        // The CountDownLatch class is a synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
        CountDownLatch latch = new CountDownLatch(1);

        // Create 3 threads, 2 of which will wait for a signal from the 3rd thread
        Thread thread1 = new Thread(new WaitingForSignalRunnable(latch), "Thread-1");
        Thread thread2 = new Thread(new WaitingForSignalRunnable(latch), "Thread-2");
        Thread thread3 = new Thread(new SignallingRunnable(latch), "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class WaitingForSignalRunnable implements Runnable {
    private final CountDownLatch latch;
    private static final Logger logger = CustomLogger.getLogger(WaitingForSignalRunnable.class);

    public WaitingForSignalRunnable(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName() + " is waiting for signal.");
        try {
            // We wait until the latch has counted down to zero
            latch.await();
            logger.info(Thread.currentThread().getName() + " received signal and is continuing.");
            // Exits the thread
        } catch (InterruptedException e) {
            logger.info(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}

class SignallingRunnable implements Runnable {
    private final CountDownLatch latch;
    private static final Logger logger = CustomLogger.getLogger(SignallingRunnable.class);

    public SignallingRunnable(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getName() + " sleeping for 1000ms.");
            Thread.sleep(1000);
            logger.info(Thread.currentThread().getName() + " sending signal to continue.");
            // Decreases the count of the latch, releasing all waiting threads when the count reaches zero
            latch.countDown();
        } catch (InterruptedException e) {
            logger.info(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}
