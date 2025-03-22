package Projekt_1;

import Projekt_1.Wyjatki.RailroadHazard;

public class Lokomotywa{

    protected String nazwa;
    protected int numerIdentyfikacyjny;
    private static int counter = 1;
    protected int maxLiczbaWagonow;
    protected double maxUciag;
    protected int maxLiczbaPDSE;
    protected Stacja stacjaMacierzysta;
    protected Stacja stacjaZrodlowa;
    protected Stacja stacjaDocelowa;
    private double predkosc;
    private Pociag pociag;
    private boolean stanTechniczny;

    public int getNumerIdentyfikacyjny() {
        return numerIdentyfikacyjny;
    }

    public void setPociag(Pociag pociag) {
        this.pociag = pociag;
    }

    public double getPredkosc() {
        return predkosc;
    }

    public void setPredkosc(double predkosc) {
        this.predkosc = predkosc;
    }

    public Lokomotywa(String nazwa, int maxLiczbaWagonow, double maxUciag, int maxLiczbaPDSE, double predkosc){

        this.nazwa = nazwa;
        this.maxLiczbaWagonow = maxLiczbaWagonow;
        this.maxUciag = maxUciag;
        this.maxLiczbaPDSE = maxLiczbaPDSE;
        this.predkosc = predkosc;
        this.numerIdentyfikacyjny = counter++;
        this.stanTechniczny = true;
    }

    public void sprawdzStanTechniczny(){
        if (stanTechniczny)
            System.out.println("Nie wymaga naprawy.");
        else
            System.out.println("Wymaga naprawy.");
    }

    public void ustalStacjeMacierzysta(Stacja stacja){
        this.stacjaMacierzysta = stacja;
    }
    public void ustalStacjeZrodlowa(Stacja stacja){
        this.stacjaZrodlowa = stacja;
    }
    public void ustalStacjeDocelowa(Stacja stacja){
        this.stacjaDocelowa = stacja;
    }

    public double zmienPredkosc(double predkosc) throws RailroadHazard{
        double zmienionaPredkosc = Math.random() > 0.5 ?
                predkosc - predkosc*0.03 : predkosc + predkosc*0.03;
        if (zmienionaPredkosc > 200)
            throw new RailroadHazard(this.pociag);
        return zmienionaPredkosc;
    }
}