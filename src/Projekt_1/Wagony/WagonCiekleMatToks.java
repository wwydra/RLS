package Projekt_1.Wagony;

import java.util.ArrayList;

public class WagonCiekleMatToks
extends WagonMatCiekle{

    private double cenaTransportu;
    protected boolean czyToksyczne;

    private double odpowiedniaTemp;
    private double temperatura;
    public WagonCiekleMatToks(String nadawca, String zabezpieczenia, double waga) {
        super(nadawca, zabezpieczenia, waga);
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setOdpowiedniaTemp(double odpowiedniaTemp) {
        this.odpowiedniaTemp = odpowiedniaTemp;
    }

    @Override
    public void dodajZbiornik(String nazwa) {
        super.dodajZbiornik(nazwa);
    }

    @Override
    public void usunZbiornik(String nazwa) {
        super.usunZbiornik(nazwa);
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

    public boolean czyOdpTemperatura(){
        if (temperatura == odpowiedniaTemp)
            return true;
        System.out.println("Odpowiednia temperatura powinna wynosic: " + odpowiedniaTemp);
        return false;
    }

    @Override
    public void przygotuj() {
        super.przygotuj();
    }

    public double getCenaTransportu() {
        return cenaTransportu;
    }

    public boolean toksycznosc(){
        czyToksyczne = true;
        return czyToksyczne;
    }

    public void obliczCeneTransportu(){
        this.cenaTransportu = wagaBrutto/100;
    }

    @Override
    public void dodajTypKontenera(String s) {
        super.dodajTypKontenera(s);
    }

    @Override
    public String getData() {
        return "Wagon na materialy ciekle toksyczne o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }
}
