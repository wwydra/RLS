package project.carriages;

public class GasesTankWagon
extends BasicGoodsWagon{

    private double permissiblePressure;
    private double pressure;
    private String mark;
    public GasesTankWagon(String shipper, String security, double weight) {
        super(shipper, security, weight);
        this.pressure = Math.random()*1000;
    }

    @Override
    public void load(String cargo, double loadWeight) {
        this.cargo = cargo;
        this.grossWeight += loadWeight;
    }

    public void markLoad(String mark){
        this.mark = mark;
    }

    public double getPermissiblePressure() {
        return permissiblePressure;
    }

    public void setPermissiblePressure(double permissiblePressure) {
        this.permissiblePressure = permissiblePressure;
    }

    public boolean checkPressure(){
        return pressure <= permissiblePressure;
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
        return "Gas materials wagon no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "shipper: " + this.shipper + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight;
    }
}
