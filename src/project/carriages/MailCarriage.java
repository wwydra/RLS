package project.carriages;

import project.interfaces.Electric;
import java.util.ArrayList;

public class MailCarriage
extends Carriage implements Electric {

    private boolean driversCabin;
    private ArrayList<String> priorityMail = new ArrayList<>();
    public MailCarriage(String shipper, String security, double weight){
        this.id = counter++;
        this.shipper = shipper;
        this.security = security;
        this.netWeight = weight;
        this.grossWeight = this.netWeight;
        this.driversCabin = false;
    }

    public boolean hasDriversCabin() {
        return driversCabin;
    }

    public ArrayList<String> getPriorityMail() {
        return priorityMail;
    }

    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void addPriorityMail(String mailName){
        priorityMail.add(mailName);
    }

    public void addDriversCabin(){
        this.driversCabin = true;
    }

    @Override
    public String getData() {
        return "Mail carriage no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }

    @Override
    public void connect() {
        this.connected = true;
    }

    @Override
    public int compareTo(Carriage c) {
        return Double.compare(this.grossWeight, c.getGrossWeight());
    }
}
