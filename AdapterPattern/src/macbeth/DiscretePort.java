package macbeth;

public class DiscretePort implements AvionicPort {

    private String port;
    private int hiV;
    private int loV;
    private String name;

    public DiscretePort(String port, int hiV, int loV, String name) {
        this.port = port;
        this.hiV = hiV;
        this.loV = loV;
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
        return "DiscretePort{" +
                "port='" + port + '\'' +
                ", hiV='" + hiV + '\'' +
                ", loV='" + loV + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
