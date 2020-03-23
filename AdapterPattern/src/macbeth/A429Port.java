package macbeth;

public class A429Port implements AvionicPort {

    private String bus;
    private int address;
    private int rate;
    private String name;

    public A429Port(String bus, int address, int rate, String name) {
        this.bus = bus;
        this.address = address;
        this.rate = rate;
        this.name = name;
    }

    @Override
    public String read() {
        return null;
    }

    @Override
    public void write(String data) {
    }

    @Override
    public String toString() {
        return "A429Port{" +
                "bus='" + bus + '\'' +
                ", address=" + address +
                ", rate=" + rate +
                ", name='" + name + '\'' +
                '}';
    }
}
