package macbeth;

public class AFDXPort implements AvionicPort {

    private AFDXPro afdx;

    public AFDXPort(String ip, int port, int vl, int subVl, String name, int refreshRate) {
        afdx = new AFDXPro(ip, port, vl, subVl, name, refreshRate, false);
    }

    @Override
    public String read() {
        return afdx.readAFDX();
    }

    @Override
    public void write(String data) {
        afdx.writeAFDX(data, -1);
    }

    @Override
    public String toString() {
        return afdx.toString();
    }
}
