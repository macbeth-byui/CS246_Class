package macbeth;

public class AFDXPro  {

    private String ip;
    private int port;
    private int vl;
    private int subVl;
    private String name;
    private int refreshRate;
    private boolean errorChecking;

    public AFDXPro(String ip, int port, int vl, int subVl, String name, int refreshRate, boolean errorChecking) {
        this.ip = ip;
        this.port = port;
        this.vl = vl;
        this.subVl = subVl;
        this.name = name;
        this.refreshRate = refreshRate;
        this.errorChecking = errorChecking;
    }

    public String readAFDX() {
        return null;
    }

    public void writeAFDX(String data, int timeout) {

    }

    @Override
    public String toString() {
        return "AFDXPro{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", vl=" + vl +
                ", subVl=" + subVl +
                ", name='" + name + '\'' +
                ", refreshRate=" + refreshRate +
                ", errorChecking=" + errorChecking +
                '}';
    }
}
