package by.etc.part6.port;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;


public class Port {
    private final static boolean PIERSES [] = new boolean[3];
    private static final Semaphore SEMAPHORE = new Semaphore(3);
    private static Port port = new Port();
    private int maxContainerNum;
    private int containerNum;
    private Queue<Ship> queue = new ArrayBlockingQueue<Ship>(100);

    private Port() {

    }

    public static Port getPort() {
        return port;
    }

    public void addShipToQueue(Ship ship) {
        queue.add(ship);
    }

    public static boolean[] getPIERSES() {
        return PIERSES;
    }

    public static Semaphore getSEMAPHORE() {
        return SEMAPHORE;
    }

    public void showQueue() {
        System.out.println(queue);
    }

    public Queue<Ship> getQueue() {
        return queue;
    }
}
