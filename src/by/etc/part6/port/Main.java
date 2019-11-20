package by.etc.part6.port;


/**
 * Многопоточность. Порт. Корабли заходят для в порт для разгрузки/загрузки контейнеров. Число контейнеров в
 * настоящий момент в порту и на корабле, должно быть неотрийательным и превышающим заданную грузоподъемность
 * судна и вместимость порта. В порту работает несколько причалов. У одного причала может стоять один корабль.
 * Корабль может загружаться у причала или разгружаться.
 */

public class Main {

    public static void main(String[] args) {
        Port port = Port.getPort();
        Port.getPort().setMaxContainerNum(1000);
        Port.getPort().setContainerNum(500);

        for (int i = 0; i < 10; i++) {
            Ship ship = Ship.generateShip(i);
            ship.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
