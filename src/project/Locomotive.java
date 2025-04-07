package project;

import project.exceptions.RailroadHazard;

public class Locomotive {

    protected String name;
    protected int id;
    private static int counter = 1;
    protected int maxNumberOfWagons;
    protected double maxTowing;
    protected int maxPDSE;
    protected Station homeStation;
    protected Station sourceStation;
    protected Station destinationStation;
    private double speed;
    private Train train;
    private boolean technicalCondition;

    public Locomotive(String name, int maxNumberOfWagons, double maxTowing, int maxPDSE, double speed){
        this.name = name;
        this.maxNumberOfWagons = maxNumberOfWagons;
        this.maxTowing = maxTowing;
        this.maxPDSE = maxPDSE;
        this.speed = speed;
        this.id = counter++;
        this.technicalCondition = true;
    }

    public void checkTechnicalCondition(){
        if (technicalCondition)
            System.out.println("Does not require repair.");
        else
            System.out.println("Requires repair.");
    }

    public double changeSpeed(double speed) throws RailroadHazard{
        double changed = Math.random() > 0.5 ?
                speed - speed*0.03 : speed + speed*0.03;
        if (changed > 200)
            throw new RailroadHazard(this.train);
        return changed;
    }

    public int getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setHomeStation(Station station){
        this.homeStation = station;
    }

    public void setSourceStation(Station station){
        this.sourceStation = station;
    }

    public void setDestination(Station station){
        this.destinationStation = station;
    }
}