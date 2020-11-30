package macbeth;

public class AFDXPort implements AvionicPort {

    private AFDXPro afdxPro;

    public AFDXPort(String ip, int port, int vl, int subVl, String name, int refreshRate) {
        afdxPro = new AFDXPro(ip, port, vl, subVl, name, refreshRate, false);
    }

    @Override
    public String read() {
        return afdxPro.readAFDX();
    }

    @Override
    public void write(String data) {
        afdxPro.writeAFDX(data, 0);
    }

    @Override
    public String toString() {
        return afdxPro.toString();
    }
}
