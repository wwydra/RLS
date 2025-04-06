package project.exceptions;

public class TooHeavyLoadException
extends Exception{

    public TooHeavyLoadException(){
        super("Too much load. Cannot add wagon.");
    }
}
