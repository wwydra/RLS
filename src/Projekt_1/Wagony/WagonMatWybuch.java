package Projekt_1.Wagony;

import java.util.ArrayList;

public class WagonMatWybuch
extends WagonTowCiezki{

    private double wilgotnosc;
    private ArrayList<String> separacje = new ArrayList<>();
    public WagonMatWybuch(String nadawca, String zabezpieczenia, double waga) {
        super(nadawca, zabezpieczenia, waga);
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void setWilgotnosc(double wilgotnosc) {
        this.wilgotnosc = wilgotnosc;
    }

    public boolean sprawdzBezpieczenstwo(){
        if (wilgotnosc > 1000) {
            System.out.println("Nalezy zmniejszyc wilgotnosc.");
            return false;
        }else
            return true;
    }

    public void dodajSeparacje(String nazwa){
        separacje.add(nazwa);
    }

    @Override
    public double getCenaTransportu() {
        return super.getCenaTransportu();
    }

    @Override
    public boolean toksycznosc() {
        return super.toksycznosc();
    }

    @Override
    public void obliczCeneTransportu() {
        super.obliczCeneTransportu();
    }

    @Override
    public String getData() {
        return "Wagon na materialy wybuchowe o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }
}
