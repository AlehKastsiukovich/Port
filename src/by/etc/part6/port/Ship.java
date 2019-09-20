package by.etc.part6.port;


import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Ship extends Thread {
    private static final Lock LOCK = new ReentrantLock();
    private static Random random = new Random();
    private int shipId;
    private int maxContainerNum;
    private int containerNum;

    public Ship(int id) {
        this.shipId = id;
    }

    public String toString() {
        return "ship" + shipId + " * containers count: " + containerNum + "/" + maxContainerNum + "(current/max)";
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
        //System.out.println(this.toString() + " loading");
        int load = random.nextInt(maxContainerNum - containerNum) + 1;
        System.out.println(this.toString() + " load - " + load + " containers.");

        if (load > Port.getContainerNum()) {
            System.out.println("There is to low containers in port. Need loading");
            Port.setContainerNum(Port.getMaxContainerNum());
        }

        Port.setContainerNum(Port.getContainerNum() - load);
        containerNum = containerNum + load;
    }

    public void unloading() {
        //System.out.println(this.toString() + " unloading");
        System.out.println("Current containers in port: " + Port.getContainerNum());
        int unload = random.nextInt(containerNum) + 1;
        System.out.println(this.toString() +" unload - " + unload + " containers.");

        if ((Port.getContainerNum() + unload) > Port.getMaxContainerNum()) {
            System.out.println("Port is overloaded! Try less.");
            unload = Port.getMaxContainerNum() - Port.getContainerNum();
        }

        Port.setContainerNum(Port.getContainerNum() + unload);
        containerNum = containerNum - unload;
    }

    public static Ship generateShip(int i) {
        Ship ship = new Ship(i);

        ship.setMaxContainerNum(random.nextInt(70) + 30);
        ship.setContainerNum(random.nextInt((ship.getMaxContainerNum() + 1))) ;

        return ship;
    }

    public void loadAndUnload() {
       // System.out.println(this.toString() +" unloading and then loading");

        unloading();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loading();
    }

    public void randomizeDecision() {
        int decision = random.nextInt(3);

        if (decision == 0) {
            loading();
        } else if (decision == 1) {
            unloading();
        } else if (decision == 2) {
            loadAndUnload();
        }
    }

    public void generateTask() {
        if (this.getContainerNum() == 0) {
            loading();
        } else if (Port.getContainerNum() == Port.getMaxContainerNum()) {
            loading();
        } else {
            randomizeDecision();
        }
    }

    public void workInPort() {
        System.out.println(this.toString() + " arrived to the port and waiting.");

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

        generateTask();

        LOCK.unlock();

        try {
            Thread.sleep(3000);
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
        workInPort();
    }
}
