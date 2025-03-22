package Projekt_1.Wagony;

import Projekt_1.Interfejsy.Elektryczny;
import Projekt_1.Wyjatki.TooManyPassengersException;

import java.util.ArrayList;

public class WagonPasazerski
extends Wagon implements Elektryczny {

    private int liczbaMiejsc;
    private int liczbaPasazerow;

    private ArrayList<Integer> numPrzedzialy = new ArrayList<>();

    public WagonPasazerski(int liczbaMiejsc, String zabezpieczenia, double waga){
        this.liczbaMiejsc = liczbaMiejsc;
        this.zabezpieczenia = zabezpieczenia;
        this.wagaNetto = waga;
        this.liczbaPasazerow = 0;
        this.wagaBrutto = this.wagaNetto;
        this.numerIdentyfikacyjny = counter++;
    }

    public void zaladuj(int liczbaOsob, double waga) throws TooManyPassengersException {
        if (this.liczbaPasazerow > this.liczbaMiejsc){
            throw new TooManyPassengersException();
        }
        this.liczbaPasazerow += liczbaOsob;
        this.wagaBrutto += waga;
    }

    public void dodajPrzedzial(int numer){
        numPrzedzialy.add(numer);
    }

    public void usunPasazera(int liczba){
        this.liczbaPasazerow -= liczba;
    }

    public int getLiczbaMiejsc() {
        return liczbaMiejsc;
    }

    public int getLiczbaPasazerow() {
        return liczbaPasazerow;
    }

    public ArrayList<Integer> getNumPrzedzialy() {
        return numPrzedzialy;
    }

    @Override
    public String getData() {
        return "Wagon pasazerski o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "liczba miejsc: " + this.liczbaMiejsc + "\n" +
                "liczba pasazerow: " + this.liczbaPasazerow + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto + "\n";
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
