package Projekt_1.Wagony;

public class WagonTowCiezki
extends Wagon {

    private double cenaTransportu;
    protected boolean czyToksyczne;
    public WagonTowCiezki(String nadawca, String zabezpieczenia, double waga){
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

    public double getCenaTransportu() {
        return cenaTransportu;
    }

    public boolean toksycznosc(){
        czyToksyczne = false;
        return czyToksyczne;
    }

    public void obliczCeneTransportu(){
        this.cenaTransportu = wagaBrutto/100;
    }

    @Override
    public String getData() {
        return "Wagon towarowy ciezki o numerze " + this.numerIdentyfikacyjny + "\n" +
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
