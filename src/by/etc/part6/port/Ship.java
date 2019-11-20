package by.etc.part6.port;


import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship extends Thread{
    private int shipId;
    private int maxContainerNum;
    private int containerNum;
    private static Lock LOCK = new ReentrantLock();
    private static Random random = new Random();

    public Ship(int id) {
        this.shipId = id;
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
        int load = random.nextInt(getMaxContainerNum() - getContainerNum()) + 1;

        System.out.println(ShipView.printShip(this) + " load - " + load + " containers.");

        if (load > Port.getPort().getContainerNum()) {
            System.out.println("There is to low containers in port. Need loading");
            Port.getPort().setContainerNum(Port.getPort().getMaxContainerNum());
        }

        Port.getPort().setContainerNum(Port.getPort().getContainerNum() - load);
        setContainerNum(getContainerNum() + load);
    }

    public void unloading() {
        System.out.println("Current containers in port: " + Port.getPort().getContainerNum());

        int unload = random.nextInt(getContainerNum()) + 1;

        System.out.println(ShipView.printShip(this) + " unload - " + unload + " containers.");

        if ((Port.getPort().getContainerNum() + unload) > Port.getPort().getMaxContainerNum()) {
            System.out.println("Port is overloaded! Try less.");
            unload = Port.getPort().getMaxContainerNum() - Port.getPort().getContainerNum();
        }

        Port.getPort().setContainerNum(Port.getPort().getContainerNum() + unload);
        setContainerNum(getContainerNum() - unload);
    }

    public static Ship generateShip(int i) {
        Ship ship = new Ship(i);

        ship.setMaxContainerNum(random.nextInt(70) + 30);
        ship.setContainerNum(random.nextInt((ship.getMaxContainerNum() + 1))) ;

        return ship;
    }

    public void loadAndUnload() {
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
        if (getContainerNum() == 0) {
            loading();
        } else if (Port.getPort().getContainerNum() == Port.getPort().getMaxContainerNum()) {
            loading();
        } else {
            randomizeDecision();
        }
    }

    public void workInPort() {

        System.out.println(ShipView.printShip(this) + " arrived to the port and waiting.");

        int numberOfPierse = -1;

        try {
            Port.getSEMAPHORE().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOCK.lock();

        for (int i = 0; i < 3; i++) {
            if (!Port.getPort().getPIERSES()[i]) {
                Port.getPort().getPIERSES()[i] = true;
                numberOfPierse = i;
                System.out.println(ShipView.printShip(this) + " arrived to the piers #" + numberOfPierse);
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
        Port.getPort().getPIERSES()[numberOfPierse] = false;
        LOCK.unlock();
        System.out.println(ShipView.printShip(this) + " leaves pierse #" + numberOfPierse);

        Port.getSEMAPHORE().release();
    }

    @Override
    public void run() {
        workInPort();
    }

    public String toString() {
        return "ship" + shipId + " * containers count: " + containerNum + "/" + maxContainerNum + "(current/max)";
    }
}
