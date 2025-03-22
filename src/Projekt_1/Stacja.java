package Projekt_1;

import java.util.HashMap;

public class Stacja
implements Comparable<Stacja>{

    private String nazwa;
    private int index;
    private static int counter = 0;
    private HashMap<Integer, Boolean> perony = new HashMap<>();

    public int getIndex() {
        return index;
    }

    public Stacja(String nazwa){
        this.nazwa = nazwa;
        this.index = counter++;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void dodajPeron(int numer, boolean czyPrzesiadkowy){
        perony.put(numer,czyPrzesiadkowy);
    }

    public HashMap<Integer, Boolean> getPerony() {
        return perony;
    }

    @Override
    public int compareTo(Stacja o) {
        return this.nazwa.compareTo(o.getNazwa()); //porownuje nazwy i zwraca ujemna wartosc jeśli String jest mniejszy od drugiego, wartość dodatnią, jeśli String jest większy
    }

    @Override
    public String toString(){
        return nazwa;
    }
}
