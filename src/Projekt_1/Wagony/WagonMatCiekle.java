package Projekt_1.Wagony;

import java.util.ArrayList;

public class WagonMatCiekle
extends WagonTowPodst{

    private int maxLiczbaZbiornikow;
    private ArrayList<String> listaZbiornikow = new ArrayList<>();
    private int count = 0;
    public WagonMatCiekle(String nadawca, String zabezpieczenia, double waga) {
        super(nadawca, zabezpieczenia, waga);
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void dodajZbiornik(String nazwa){
        if (count < maxLiczbaZbiornikow){
            listaZbiornikow.add(nazwa);
            count++;
        }else{
            System.out.println("Osiagnieto maksymalna liczbe zbiornikow.");
        }
    }

    public void usunZbiornik(String nazwa){
        if (listaZbiornikow.contains(nazwa))
            listaZbiornikow.remove(nazwa);
        else
            System.out.println("Nie ma zbiornika o podanej nazwie.");
    }

    public void setMaxLiczbaZbiornikow(int maxLiczbaZbiornikow) {
        this.maxLiczbaZbiornikow = maxLiczbaZbiornikow;
    }

    public ArrayList<String> getListaZbiornikow() {
        return listaZbiornikow;
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
        return "Wagon na materialy ciekle o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }
}
