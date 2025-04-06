package project.carriages;

public class BaggagePostalCarriage
extends MailCarriage{

    private int luggageCount;
    private boolean international;

    public BaggagePostalCarriage(String shipper, String security, double weight){
        super(shipper, security, weight);
        this.luggageCount = 0;
    }

    @Override
    public void addPriorityMail(String mailName) {
        super.addPriorityMail(mailName);
    }

    public void grantInternationalAccessibility() {
        this.international = true;
    }

    public void takeBackInternationalAccessibility() {
        this.international = false;
    }

    @Override
    public void connect() {
        super.connect();
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    @Override
    public void addDriversCabin() {
        super.addDriversCabin();
    }

    public void getBaggageCount(){
        this.luggageCount = (int)grossWeight/20;
    }

    @Override
    public String getData() {
        return "Baggage and postal carriage no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}
