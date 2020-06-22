package macbeth;

public class AFDXPort implements AvionicPort {

    private String ip;
    private int port;
    private int vl;
    private int subVl;
    private String name;
    private int refreshRate;

    public AFDXPort(String ip, int port, int vl, int subVl, String name, int refreshRate) {
        this.ip = ip;
        this.port = port;
        this.vl = vl;
        this.subVl = subVl;
        this.name = name;
        this.refreshRate = refreshRate;
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
        return "AFDXPort{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", vl=" + vl +
                ", subVl=" + subVl +
                ", name='" + name + '\'' +
                ", refreshRate=" + refreshRate +
                '}';
    }
}
