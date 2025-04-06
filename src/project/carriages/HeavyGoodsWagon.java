package project.carriages;

public class HeavyGoodsWagon
extends Carriage {

    private double transportPrice;
    protected boolean toxic;
    public HeavyGoodsWagon(String shipper, String security, double weight){
        this.id = counter++;
        this.shipper = shipper;
        this.security = security;
        this.netWeight = weight;
        this.grossWeight = this.netWeight;
    }

    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public double getTransportPrice() {
        return transportPrice;
    }

    public boolean toxicity(){
        toxic = false;
        return toxic;
    }

    public void calculateTransportPrice(){
        this.transportPrice = grossWeight/100;
    }

    @Override
    public String getData() {
        return "Heavy goods wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }

    @Override
    public int compareTo(Carriage c) {
        return Double.compare(this.grossWeight, c.getGrossWeight());
    }
}
