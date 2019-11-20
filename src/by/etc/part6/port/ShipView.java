package by.etc.part6.port;


public class ShipView {

    public static String printShip(Ship ship) {
        return "ship" + ship.getShipId() + " * containers count: "
                + ship.getContainerNum() + "/" + ship.getMaxContainerNum() + "(current/max)";
    }
}
