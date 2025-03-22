package Projekt_1.Wagony;

import Projekt_1.Interfejsy.Elektryczny;

import java.util.ArrayList;

public class WagonChlodniczy
extends WagonTowPodst implements Elektryczny {

    private double temperatura;
    private double wysokoscZamrazania = -10;
    public WagonChlodniczy(String nadawca, String zabezpieczenia, double waga) {
        super(nadawca, zabezpieczenia, waga);
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void setWysokoscZamrazania(double wysokoscZamrazania) {
        this.wysokoscZamrazania = wysokoscZamrazania;
    }

    public boolean czyZamrozone(){
        if (temperatura <= wysokoscZamrazania)
            return true;
        return false;
    }

    @Override
    public boolean isGotowosc() {
        return super.isGotowosc();
    }

    @Override
    public String getAktualTypKontenera() {
        return super.getAktualTypKontenera();
    }

    @Override
    public ArrayList<Integer> getKontenery() {
        return super.getKontenery();
    }

    @Override
    public String getData() {
        return "Wagon chlodniczy o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public boolean sprawdzTemperature(){
        if (this.temperatura > 0){
            return false;
        }
        return true;
    }

    @Override
    public void przygotuj() {
        super.przygotuj();
    }

    @Override
    public void dodajTypKontenera(String s) {
        super.dodajTypKontenera(s);
    }

    @Override
    public void podlacz() {
        this.podlaczenie = true;
    }
}
