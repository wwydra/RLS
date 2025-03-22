package Projekt_1.Wagony;

import Projekt_1.Interfejsy.Elektryczny;

import java.util.ArrayList;

public class WagonRestauracyjny
extends Wagon implements Elektryczny {

    private ArrayList<String> listaPosilkow = new ArrayList<>();
    private boolean chlodzenie;

    public WagonRestauracyjny(String nadawca, String zabezpieczenia, double waga){
        this.numerIdentyfikacyjny = counter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wagaNetto = waga;
        this.wagaBrutto = this.wagaNetto;
    }

    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void dodajPosilek(String posilek){
        listaPosilkow.add(posilek);
    }

    public String wydajPosilek(String posilek){
        if (listaPosilkow.contains(posilek))
            return posilek;
        else {
            System.out.println("Posilek " + posilek + " nie jest serwowany.");
            return null;
        }
    }

    public void wlaczChlodzenie(){
        this.chlodzenie = true;
    }

    public void wylaczChlodzenie(){
        this.chlodzenie = false;
    }

    @Override
    public void podlacz() {
        this.podlaczenie = true;
    }

    @Override
    public String getData() {
        return "Wagon restauracyjny o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }

    @Override
    public int compareTo(Wagon o) {
        return Double.compare(this.wagaBrutto, o.getWagaBrutto());
    }
}
