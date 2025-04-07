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

    public boolean toxicity(){
        toxic = false;
        return toxic;
    }

    public void calculateTransportPrice(){
        this.transportPrice = grossWeight/100;
    }

    public double getTransportPrice() {
        return transportPrice;
    }

    @Override
    public String getData() {
        return "Heavy goods wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}