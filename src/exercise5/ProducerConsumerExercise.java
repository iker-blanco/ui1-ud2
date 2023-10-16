package exercise5;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import utils.CustomLogger;

public class ProducerConsumerExercise {

    // Both MAX_SIZE and NUMBERS_TO_PRODUCE are constants, so we can use static final to declare them
    private static final int MAX_SIZE = 10;
    private static final int NUMBERS_TO_PRODUCE = 100;

    public static void main(String[] args) {
        // We use a ConcurrentLinkedQueue to store the values
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        // We use AtomicInteger to store the sum and count, so that we can use them in multiple threads
        // Integers are not thread-safe, so we cannot use them in multiple threads
        AtomicInteger sum = new AtomicInteger(0);
        AtomicInteger count = new AtomicInteger(0);

        Thread producer = new Thread(new Producer(queue, MAX_SIZE, NUMBERS_TO_PRODUCE));
        Thread consumer1 = new Thread(new SumConsumer(queue, sum, count));
        Thread consumer2 = new Thread(new AverageDisplay(sum, count));

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}

class Producer implements Runnable {
    private final ConcurrentLinkedQueue<Integer> queue;
    private final int maxSize;
    private final int numbersToProduce;
    private static final Logger logger = CustomLogger.getLogger(Producer.class);

    public Producer(ConcurrentLinkedQueue<Integer> queue, int maxSize, int numbersToProduce) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.numbersToProduce = numbersToProduce;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < numbersToProduce; i++) {
                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        logger.info("Queue is full, waiting...");
                        queue.wait();
                    }

                    Integer value = (int) (Math.random() * 100);
                    queue.add(value);
                    logger.info("Queueing the " + i + "th value: " + value);
                    queue.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            logger.warning("Producer was interrupted");
        }
    }
}

class SumConsumer implements Runnable {
    private final ConcurrentLinkedQueue<Integer> queue;
    // Use AtomicInteger to store the sum and count, so that we can use them in multiple threads
    // Integers are not thread-safe, so we cannot use them in multiple threads
    private final AtomicInteger sum;
    private final AtomicInteger count;
    private static final Logger logger = CustomLogger.getLogger(SumConsumer.class);

    public SumConsumer(ConcurrentLinkedQueue<Integer> queue, AtomicInteger sum, AtomicInteger count) {
        this.queue = queue;
        this.sum = sum;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            // TODO: add an end condition for the loop so that the consumer will stop when the producer finishes
            while (true) {
                Integer value;
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        logger.info("Queue is empty, waiting...");
                        queue.wait();
                    }

                    value = queue.poll();
                    logger.info("Consuming value: " + value);
                    // Notify the producer that the queue is not full anymore
                    queue.notify();
                }

                sum.addAndGet(value);
                count.incrementAndGet();
                // sleep a random amount of time between 0 and 10 milliseconds
                Thread.sleep((int) (Math.random() * 10));

            }
            // sleep a random amount of time between 0 and 100 milliseconds
        } catch (InterruptedException e) {
            logger.warning("Consumer was interrupted");
        }
    }
}

class AverageDisplay implements Runnable {
    private final AtomicInteger sum;
    private final AtomicInteger count;
    private static final Logger logger = CustomLogger.getLogger(SumConsumer.class);

    public AverageDisplay(AtomicInteger sum, AtomicInteger count) {
        this.sum = sum;
        this.count = count;
    }

    @Override
    public void run() {
        // Use Swing to display the average
        JFrame frame = new JFrame("Average Display");
        JLabel label = new JLabel();
        label.setFont(new Font("Verdana", Font.BOLD, 24));
        frame.add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            // TODO: add an end condition for the loop so that the display will stop when the producer finishes
            while (true) {
                // Calculate the average with the current sum and count
                int currentCount = count.get();
                float average = currentCount > 0 ? (float) sum.get() / currentCount : 0;
                label.setText("Average: " + average);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            logger.warning("The average display was interrupted.");
        }
    }
}
