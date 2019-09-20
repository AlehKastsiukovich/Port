package by.etc.part6.port;


import java.util.concurrent.Semaphore;


public class Port {
    private static final boolean PIERSES [] = new boolean[3];
    private static final Semaphore SEMAPHORE = new Semaphore(3);
    private static Port port = new Port();
    private static int maxContainerNum;
    private static int containerNum;

    private Port() {

    }

    public static int getMaxContainerNum() {
        return maxContainerNum;
    }

    public static void setMaxContainerNum(int maxContainerNum) {
        Port.maxContainerNum = maxContainerNum;
    }

    public static int getContainerNum() {
        return containerNum;
    }

    public static void setContainerNum(int containerNum) {
        Port.containerNum = containerNum;
    }

    public static Port getPort() {
        return port;
    }

    public static boolean[] getPIERSES() {
        return PIERSES;
    }

    public static Semaphore getSEMAPHORE() {
        return SEMAPHORE;
    }
}
