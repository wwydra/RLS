package project.carriages;

import project.interfaces.Electric;

import java.util.ArrayList;

public class RestaurantCarriage
extends Carriage implements Electric {

    private final ArrayList<String> mealList = new ArrayList<>();
    private boolean cooling;

    public RestaurantCarriage(String shipper, String security, double weight){
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

    public void addMeal(String meal){
        mealList.add(meal);
    }

    public String serveAMeal(String meal){
        if (mealList.contains(meal))
            return meal;
        else {
            System.out.println("Meal " + meal + " is not available.");
            return null;
        }
    }

    public void turnCoolingOn(){
        this.cooling = true;
    }

    public void turnCoolingOff(){
        this.cooling = false;
    }

    @Override
    public void connect() {
        this.connected = true;
    }

    @Override
    public String getData() {
        return "Restaurant carriage no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}