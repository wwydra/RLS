package project.carriages;

import java.util.ArrayList;

public class ToxicLiquidsTankWagon
extends LiquidsTankWagon {

    private double transportPrice;
    protected boolean toxic;

    private double properTemperature;
    private double temperature;
    public ToxicLiquidsTankWagon(String shipper, String security, double weight) {
        super(shipper, security, weight);
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setProperTemperature(double properTemperature) {
        this.properTemperature = properTemperature;
    }

    @Override
    public void addTank(String name) {
        super.addTank(name);
    }

    @Override
    public void removeTank(String name) {
        super.removeTank(name);
    }

    @Override
    public boolean isReady() {
        return super.isReady();
    }

    @Override
    public String getCurrContainerType() {
        return super.getCurrContainerType();
    }

    @Override
    public ArrayList<Integer> getContainers() {
        return super.getContainers();
    }

    public boolean isTemperatureAppropriate(){
        if (temperature == properTemperature)
            return true;
        System.out.println("The proper temperature should be: " + properTemperature);
        return false;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    public double getTransportPrice() {
        return transportPrice;
    }

    public boolean toxicity(){
        toxic = true;
        return toxic;
    }

    public void calculateTransportPrice(){
        this.transportPrice = grossWeight/100;
    }

    @Override
    public void addContainerType(String s) {
        super.addContainerType(s);
    }

    @Override
    public String getData() {
        return "Wagon for toxic liquid materials no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}
