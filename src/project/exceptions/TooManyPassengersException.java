package project.exceptions;

public class TooManyPassengersException
extends Exception{

    public TooManyPassengersException(){
        super("Too many passengers.");
    }
}
