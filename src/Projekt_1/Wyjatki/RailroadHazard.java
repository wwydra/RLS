package Projekt_1.Wyjatki;

import Projekt_1.Pociag;

public class RailroadHazard
extends Exception{
    public RailroadHazard(Pociag pociag) {
        super("Pzekroczono 200 km/h.\n" + pociag.print());

    }

}
