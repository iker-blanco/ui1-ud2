package exercise5;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import utils.CustomLogger;

public class ProducerConsumerExercise {

    // Both MAX_SIZE and NUMBERS_TO_PRODUCE are constants, so we can use static final to declare them
    private static final int MAX_SIZE = 10;
    private static final int NUMBERS_TO_PRODUCE = 5000;

    public static void main(String[] args) {
        // We use a LinkedList as the queue to store the numbers
        Queue<Integer> queue = new LinkedList<>();
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
    private final Queue<Integer> queue;
    private final int maxSize;
    private final int numbersToProduce;
    private static final Logger logger = CustomLogger.getLogger(Producer.class);

    public Producer(Queue<Integer> queue, int maxSize, int numbersToProduce) {
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
                        // The queue is full, so the producer should wait
                        logger.info("Queue is full, " + Thread.currentThread().getName() + " is waiting.");
                        queue.wait();
                    }
                    // generate a random number between 0 and 100
                    Integer value = (int) (Math.random() * 100);
                    logger.info("Sending the " + i + "th value to queue: " + value);
                    queue.add(value);
                    queue.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            logger.warning("The producer was interrupted.");
        }
    }
}

class SumConsumer implements Runnable {
    private final Queue<Integer> queue;
    // Use AtomicInteger to store the sum and count, so that we can use them in multiple threads
    // Integers are not thread-safe, so we cannot use them in multiple threads
    private final AtomicInteger sum;
    private final AtomicInteger count;
    private static final Logger logger = CustomLogger.getLogger(SumConsumer.class);

    public SumConsumer(Queue<Integer> queue, AtomicInteger sum, AtomicInteger count) {
        this.queue = queue;
        this.sum = sum;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            // TODO: add an end condition for the loop so that the consumer will stop when the producer finishes
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        logger.info("Queue is empty, " + Thread.currentThread().getName() + " is waiting.");
                        queue.wait();
                    }
                    int value = queue.poll();
                    logger.info("Consuming value: " + value);
                    // Sleep for 100 milliseconds to make the code more observable
                    Thread.sleep(100);
                    sum.addAndGet(value);
                    count.incrementAndGet();
                    queue.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            logger.warning("The consumer was interrupted.");
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
