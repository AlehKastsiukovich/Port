package by.etc.part6.port;


import java.util.concurrent.Semaphore;

public class Port {
    private static final boolean PIERSES [] = new boolean[3];
    private static final Semaphore SEMAPHORE = new Semaphore(3);
    private static Port port;
    private int maxContainerNum;
    private int containerNum;

    private Port() {

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

    public static synchronized Port getPort() {
        if (port == null) {
            port = new Port();
        }

        return port;
    }

    public boolean[] getPIERSES() {
        return PIERSES;
    }

    public static Semaphore getSEMAPHORE() {
        return SEMAPHORE;
    }
}
