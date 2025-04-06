package project.carriages;

import java.util.ArrayList;

public class BasicGoodsWagon
extends Carriage {

    private boolean readiness;
    private String currContainerType;
    private ArrayList<Integer> containers;
    private static int count = 1;
    public BasicGoodsWagon(String shipper, String security, double weight){
        this.id = counter++;
        this.shipper = shipper;
        this.security = security;
        this.netWeight = weight;
        this.grossWeight = this.netWeight;
        this.readiness = true;
    }

    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
        this.readiness = false;
    }

    public boolean isReady() {
        return readiness;
    }

    public String getCurrContainerType() {
        return currContainerType;
    }

    public ArrayList<Integer> getContainers() {
        return containers;
    }

    public void prepare(){
        this.cargo = null;
        this.grossWeight = netWeight;
        this.readiness = true;
    }

    public void addContainerType(String s){
        this.currContainerType = s;
        containers.add(count++);
    }

    @Override
    public String getData() {
        return "Basic goods wagon no. " + this.id + "\n" +
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
