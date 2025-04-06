package project.carriages;

import project.interfaces.Electric;
import project.exceptions.TooManyPassengersException;

import java.util.ArrayList;

public class PassengerCarriage
extends Carriage implements Electric {

    private int numberOfSeats;
    private int numberOfPassengers;

    private ArrayList<Integer> compartmentNumbers = new ArrayList<>();

    public PassengerCarriage(int numberOfSeats, String security, double weight){
        this.numberOfSeats = numberOfSeats;
        this.security = security;
        this.netWeight = weight;
        this.numberOfPassengers = 0;
        this.grossWeight = this.netWeight;
        this.id = counter++;
    }

    public void load(int numberOfPeople, double weight) throws TooManyPassengersException {
        if (this.numberOfPassengers > this.numberOfSeats){
            throw new TooManyPassengersException();
        }
        this.numberOfPassengers += numberOfPeople;
        this.grossWeight += weight;
    }

    public void addCompartment(int number){
        compartmentNumbers.add(number);
    }

    public void deletePassengers(int count){
        this.numberOfPassengers -= count;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public ArrayList<Integer> getCompartmentNumbers() {
        return compartmentNumbers;
    }

    @Override
    public String getData() {
        return "Passenger carriage no. " + this.id + "\n" +
                "cargo: " + this.cargo + "\n" +
                "number of seats: " + this.numberOfSeats + "\n" +
                "number of passengers: " + this.numberOfPassengers + "\n" +
                "security: " + this.security + "\n" +
                "weight: " + this.grossWeight + "\n";
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
