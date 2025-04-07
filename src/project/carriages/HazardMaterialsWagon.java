package project.carriages;

public class HazardMaterialsWagon
extends HeavyGoodsWagon{

    private String password;
    private boolean isolation;

    public HazardMaterialsWagon(String shipper, String security, double weight) {
        super(shipper, security, weight);
        this.isolation = false;
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void changeLoad(String password, String cargo){
        if (password.equals(this.password))
            this.cargo = cargo;
        else
            System.out.println("Access denied.");
    }

    public void changeIsolationStatus(boolean status){
        this.isolation = status;
    }

    @Override
    public boolean toxicity() {
        toxic = true;
        return toxic;
    }

    @Override
    public void calculateTransportPrice() {
        super.calculateTransportPrice();
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String getData() {
        return "Toxic materials wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}
