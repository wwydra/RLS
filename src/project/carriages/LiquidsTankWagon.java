package project.carriages;

import java.util.ArrayList;

public class LiquidsTankWagon
extends BasicGoodsWagon{

    private int maxTanks;
    private ArrayList<String> tanksList = new ArrayList<>();
    private int count = 0;
    public LiquidsTankWagon(String shipper, String security, double weight) {
        super(shipper, security, weight);
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void addTank(String name){
        if (count < maxTanks){
            tanksList.add(name);
            count++;
        }else{
            System.out.println("Maximum number of tanks reached.");
        }
    }

    public void removeTank(String name){
        if (tanksList.contains(name))
            tanksList.remove(name);
        else
            System.out.println("There is no tank with the given name.");
    }

    public void setMaxTanks(int maxTanks) {
        this.maxTanks = maxTanks;
    }

    public ArrayList<String> getTanksList() {
        return tanksList;
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
    public String getData() {
        return "Liquid materials wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}
