package Projekt_1.Wagony;

import Projekt_1.Interfejsy.Elektryczny;

import java.util.ArrayList;

public class WagonPocztowy
extends Wagon implements Elektryczny {

    private boolean kabinaMaszynisty;
    private ArrayList<String> przesylkiPriorytet = new ArrayList<>();
    public WagonPocztowy(String nadawca, String zabezpieczenia, double waga){
        this.numerIdentyfikacyjny = counter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wagaNetto = waga;
        this.wagaBrutto = this.wagaNetto;
        this.kabinaMaszynisty = false;
    }

    public boolean isKabinaMaszynisty() {
        return kabinaMaszynisty;
    }

    public ArrayList<String> getPrzesylkiPriorytet() {
        return przesylkiPriorytet;
    }

    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void dodajPrzesylkePriorytet(String nazwaPrzesylki){
        przesylkiPriorytet.add(nazwaPrzesylki);
    }

    public void dodajKabineMaszynisty(){
        this.kabinaMaszynisty = true;
    }

    @Override
    public String getData() {
        return "Wagon pocztowy o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }

    @Override
    public void podlacz() {
        this.podlaczenie = true;
    }

    @Override
    public int compareTo(Wagon o) {
        return Double.compare(this.wagaBrutto, o.getWagaBrutto());
    }
}
