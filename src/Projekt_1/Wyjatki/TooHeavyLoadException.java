package Projekt_1.Wyjatki;

public class TooHeavyLoadException
extends Exception{

    public TooHeavyLoadException(){
        super("Zbyt duze obciazenie. Nie mozna dodac wagonu.");
    }
}
