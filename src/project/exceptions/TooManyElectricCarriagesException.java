package project.exceptions;

public class TooManyElectricCarriagesException
extends Exception{

    public TooManyElectricCarriagesException(){
        super("Too many wagons requiring connection to the locomotive's electrical network.");
    }
}
