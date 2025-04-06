package project.carriages;

import project.interfaces.Electric;

import java.util.ArrayList;

public class RefrigeratedWagon
extends BasicGoodsWagon implements Electric {

    private double temperature;
    private double freezingPoint = -10;
    public RefrigeratedWagon(String shipper, String security, double weight) {
        super(shipper, security, weight);
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void setFreezingPoint(double freezingPoint) {
        this.freezingPoint = freezingPoint;
    }

    public boolean isFrozen(){
        return temperature <= freezingPoint;
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

    @Override
    public String getData() {
        return "Refrigerated wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean checkTemperature(){
        return !(this.temperature > 0);
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    public void addContainerType(String s) {
        super.addContainerType(s);
    }

    @Override
    public void connect() {
        this.connected = true;
    }
}
