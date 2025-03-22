package Projekt_1.Wagony;

public class WagonMatGazowe
extends WagonTowPodst{

    private double dopuszczalneCisnienie;
    private double cisnienie;
    private String oznaczenie;
    public WagonMatGazowe(String nadawca, String zabezpieczenia, double waga) {
        super(nadawca, zabezpieczenia, waga);
        this.cisnienie = Math.random()*1000;
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void oznaczLadunek(String oznaczenie){
        this.oznaczenie = oznaczenie;
    }

    public double getDopuszczalneCisnienie() {
        return dopuszczalneCisnienie;
    }

    public void setDopuszczalneCisnienie(double dopuszczalneCisnienie) {
        this.dopuszczalneCisnienie = dopuszczalneCisnienie;
    }

    public boolean sprawdzCisnienie(){
        if (cisnienie <= dopuszczalneCisnienie)
            return true;
        else
            return false;
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
    public String getData() {
        return "Wagon na materialy gazowe o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }
}
