package Projekt_1.Wyjatki;

public class TooManyElectricCarriagesException
extends Exception{

    public TooManyElectricCarriagesException(){
        super("Za duzo wagonow wymagajacych podlaczenia do sieci elektrycznej lokomotywy.");
    }
}
