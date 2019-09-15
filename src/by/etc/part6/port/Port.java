package by.etc.part6.port;


public class Port {
    private Pier [] piers;
    private Container [] capacity;

    public Port(int numberOfPiers, int contCapacity) {
        piers = new Pier[numberOfPiers];
        for (int i = 0; i < numberOfPiers; i++) {
            piers[i] = new Pier();
            piers[i].setNumber(i);
        }

        capacity = new Container[contCapacity];
        for (int i = 0; i < contCapacity; i++) {
            capacity[i] = new Container();
        }
    }

    public Pier[] getPiers() {
        return piers;
    }

    public void setPiers(Pier[] piers) {
        this.piers = piers;
    }

    public Container[] getCapacity() {
        return capacity;
    }

    public void setCapacity(Container[] capacity) {
        this.capacity = capacity;
    }
}
