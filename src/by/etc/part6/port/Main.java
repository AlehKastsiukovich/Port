package by.etc.part6.port;


public class Main {
    public static void main(String[] args) {
        Port port = Port.getPort();
        Port.setMaxContainerNum(1000);
        Port.setContainerNum(500);

        for (int i = 0; i < 10; i++) {
            Ship ship = Ship.generateShip(i);
            ship.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
