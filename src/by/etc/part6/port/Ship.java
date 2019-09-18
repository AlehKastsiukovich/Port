package by.etc.part6.port;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Ship extends Thread{
    private static final Lock LOCK = new ReentrantLock();
    private int shipId;
    private int maxContainerNum;
    private int containerNum;

    public Ship(int id) {
        this.shipId = id;
    }

    public String toString() {
        return "ship" + shipId + " cont: " + containerNum + "/" + maxContainerNum;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public int getMaxContainerNum() {
        return maxContainerNum;
    }

    public void setMaxContainerNum(int maxContainerNum) {
        this.maxContainerNum = maxContainerNum;
    }

    public int getContainerNum() {
        return containerNum;
    }

    public void setContainerNum(int containerNum) {
        this.containerNum = containerNum;
    }

    public void loading() {

    }

    public void unloading() {

    }

    public void work() {

    }

    public void goToPort() {
        System.out.println(this.toString() + " arrived to the port");

        int numberOfPierse = -1;

        try {
            Port.getSEMAPHORE().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOCK.lock();

        for (int i = 0; i < 3; i++) {
            if (!Port.getPIERSES()[i]) {
                Port.getPIERSES()[i] = true;
                numberOfPierse = i;
                System.out.println(this.toString() + " arrived to the piers #" + numberOfPierse);
                break;
            }
        }

        LOCK.unlock();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOCK.lock();
        Port.getPIERSES()[numberOfPierse] = false;
        LOCK.unlock();
        System.out.println(this.toString() + " leaves pierse #" + numberOfPierse);

        Port.getSEMAPHORE().release();
    }

    @Override
    public void run() {
       goToPort();
    }
}
