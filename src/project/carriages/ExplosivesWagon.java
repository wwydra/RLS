package project.carriages;

import java.util.ArrayList;

public class ExplosivesWagon
extends HeavyGoodsWagon{

    private double humidity;
    private ArrayList<String> separations = new ArrayList<>();
    public ExplosivesWagon(String shipper, String security, double weight) {
        super(shipper, security, weight);
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public boolean checkSecurity(){
        if (humidity > 1000) {
            System.out.println("Humidity should be reduced.");
            return false;
        }else
            return true;
    }

    public void addSeparation(String name){
        separations.add(name);
    }

    @Override
    public double getTransportPrice() {
        return super.getTransportPrice();
    }

    @Override
    public boolean toxicity() {
        return super.toxicity();
    }

    @Override
    public void calculateTransportPrice() {
        super.calculateTransportPrice();
    }

    @Override
    public String getData() {
        return "Explosives wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}
