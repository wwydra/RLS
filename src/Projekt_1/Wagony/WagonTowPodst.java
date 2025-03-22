package Projekt_1.Wagony;

import java.util.ArrayList;

public class WagonTowPodst
extends Wagon {

    private boolean gotowosc;
    private String aktualTypKontenera;
    private ArrayList<Integer> kontenery;
    private static int count = 1;
    public WagonTowPodst(String nadawca, String zabezpieczenia, double waga){
        this.numerIdentyfikacyjny = counter++;
        this.nadawca = nadawca;
        this.zabezpieczenia = zabezpieczenia;
        this.wagaNetto = waga;
        this.wagaBrutto = this.wagaNetto;
        this.gotowosc = true;
    }

    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
        this.gotowosc = false;
    }

    public boolean isGotowosc() {
        return gotowosc;
    }

    public String getAktualTypKontenera() {
        return aktualTypKontenera;
    }

    public ArrayList<Integer> getKontenery() {
        return kontenery;
    }

    public void przygotuj(){
        this.ladunek = null;
        this.wagaBrutto = wagaNetto;
        this.gotowosc = true;
    }

    public void dodajTypKontenera(String s){
        this.aktualTypKontenera = s;
        kontenery.add(count++);
    }

    @Override
    public String getData() {
        return "Wagon towarowy podstawowy o numerze " + this.numerIdentyfikacyjny + "\n" +
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
