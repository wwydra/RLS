package project.exceptions;

public class TooManyCarriagesException
extends Exception{

    public TooManyCarriagesException(){
        super("Can't add another wagon.");
    }
}
