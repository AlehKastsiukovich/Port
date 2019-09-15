package by.etc.part6.port;


public class Main {
    public static void main(String[] args) {
        Port port = new Port(3, 1000);

        System.out.println(port.getCapacity().length);
        System.out.println(port.getPiers().length);
    }
}
