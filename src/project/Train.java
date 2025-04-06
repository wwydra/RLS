package project;

import project.carriages.Carriage;
import project.exceptions.TooHeavyLoadException;
import project.exceptions.TooManyCarriagesException;
import project.exceptions.TooManyElectricCarriagesException;

import java.util.*;

public class Train
implements Comparable<Train>{

    protected Locomotive locomotive;
    public ArrayList<Carriage> carriages;
    protected int connectedWagonsCount;
    protected int PDSECount;
    protected double towingCapacity;
    public int number;
    private static int counter = 1;
    protected ArrayList<Station> route;
    private int distance;
    private TreeMap<Station, HashMap<Station, Integer>> stationMap;

    public Train(Locomotive locomotive, TreeMap<Station, HashMap<Station, Integer>> stationMap) {
        this.locomotive = locomotive;
        this.carriages = new ArrayList<>();
        this.number = counter++;
        this.connectedWagonsCount = 0;
        this.PDSECount = 0;
        this.towingCapacity = 0;
        this.stationMap = stationMap;
    }
    public ArrayList<Station> getRoute() {
        return route;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public int getDistance(){
        for (int i = 0; i < route.size() - 1; i++){
            distance += stationMap.get(route.get(i)).get(route.get(i + 1));
        }
        return distance;
    }

    public void addCarriage(Carriage carriage) throws TooManyCarriagesException, TooManyElectricCarriagesException, TooHeavyLoadException {
        if (this.connectedWagonsCount >= locomotive.maxNumberOfWagons){
            throw new TooManyCarriagesException();
        }
        if (this.PDSECount >= locomotive.maxPDSE){
            throw new TooManyElectricCarriagesException();
        }
        if (this.towingCapacity >= locomotive.maxTowing){
            throw new TooHeavyLoadException();
        }

        carriages.add(carriage);
        this.connectedWagonsCount++;
        this.towingCapacity += carriage.getGrossWeight();
        if (carriage.isConnected()){
            this.PDSECount++;
        }
    }

    public void removeCarriage(String cargo){
        for (Carriage carriage : carriages){
            if (carriage.getCargo() != null && carriage.getCargo().equals(cargo)){
                carriages.remove(carriage);
                return;
            }
        }
    }

    public void establishRoute(ArrayList<Station> route){
        this.route = route;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();

        sb.append("Train no. ").append(this.number).append(": locomotive no. ").append(this.locomotive.id).append("\n").append("connected carriages: \n");
        sb.append("\n");
        for (Carriage carriage : carriages){
            if (carriage != null) {
                sb.append(carriage.getData()).append("\n").append("\n");
            }
        }
        sb.append("Starting station: ").append(this.locomotive.sourceStation).append("\n").append("Destination station: ").append(this.locomotive.destinationStation).append("\n");
        if (this.route != null)
            sb.append("Distance to cover: ").append(getDistance());
        else
            sb.append("Distance to cover: ").append(0);

        return sb.toString();
    }

    @Override
    public int compareTo(Train t) {
        return Integer.compare(this.getDistance(), t.getDistance());
    }
}

