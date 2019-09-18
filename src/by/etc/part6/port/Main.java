package by.etc.part6.port;


public class Main {
    public static void main(String[] args) {
        Port port = Port.getPort();

        for (int i = 0; i < 10; i++) {
            new Ship(i).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
