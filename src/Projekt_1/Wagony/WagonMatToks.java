package Projekt_1.Wagony;

public class WagonMatToks
extends WagonTowCiezki{

    private String hasloDostepu;
    private boolean izolacja;
    public WagonMatToks(String nadawca, String zabezpieczenia, double waga) {
        super(nadawca, zabezpieczenia, waga);
        this.izolacja = false;
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    public void ustalHaslo(String haslo){
        this.hasloDostepu = haslo;
    }

    public void zamienLadunek(String haslo, String ladunek){
        if (haslo.equals(hasloDostepu))
            this.ladunek = ladunek;
        else
            System.out.println("Odmowa dostepu.");
    }

    public void zmienStanIzolacji(boolean stan){
        this.izolacja = stan;
    }

    @Override
    public boolean toksycznosc() {
        czyToksyczne = true;
        return czyToksyczne;
    }

    @Override
    public void obliczCeneTransportu() {
        super.obliczCeneTransportu();
    }

    @Override
    public String getData() {
        return "Wagon na materialy toksyczne o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }
}
