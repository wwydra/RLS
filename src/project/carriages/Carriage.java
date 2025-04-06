package project.carriages;

public abstract class Carriage
        implements Comparable<Carriage>{

    protected int id;
    protected String shipper;
    protected String security;
    protected double grossWeight;
    protected double netWeight;
    protected static int counter = 1;
    protected boolean connected = false;
    protected String cargo;

    public abstract String getData();

    public double getGrossWeight() {
        return grossWeight;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public int compareTo(Carriage c) {
        return Double.compare(this.grossWeight, c.getGrossWeight());
    }
}
