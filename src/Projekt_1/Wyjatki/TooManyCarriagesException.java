package Projekt_1.Wyjatki;

public class TooManyCarriagesException
extends Exception{

    public TooManyCarriagesException(){
        super("Nie mozna dodac kolejnego wagonu.");
    }
}
