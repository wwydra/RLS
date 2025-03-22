package Projekt_1.Wagony;

public class WagonBagazowoPocztowy
extends WagonPocztowy{

    private int liczbaBagazy;
    private boolean dostepnoscMiedzynarodowa;

    public WagonBagazowoPocztowy(String nadawca, String zabezpieczenia, double waga){
        super(nadawca, zabezpieczenia, waga);
        this.liczbaBagazy = 0;
    }

    @Override
    public void dodajPrzesylkePriorytet(String nazwaPrzesylki) {
        super.dodajPrzesylkePriorytet(nazwaPrzesylki);
    }

    public void dajDostepMiedzynarodowy(){
        this.dostepnoscMiedzynarodowa = true;
    }

    public void odbierzDostepMiedzynarodowy(){
        this.dostepnoscMiedzynarodowa = false;
    }

    @Override
    public void podlacz() {
        super.podlacz();
    }

    @Override
    public void zaladuj(String ladunek, double wagaLadunku) {
        this.ladunek = ladunek;
        this.wagaBrutto += wagaLadunku;
    }

    @Override
    public void dodajKabineMaszynisty() {
        super.dodajKabineMaszynisty();
    }

    //liczone przy sredniej wadze bagazu
    public void obliczLiczbeBagazy(){
        this.liczbaBagazy = (int)wagaBrutto/20;
    }

    @Override
    public String getData() {
        return "Wagon bagazowo-pocztowy o numerze " + this.numerIdentyfikacyjny + "\n" +
                "ladunek: " + this.ladunek + "\n" +
                "nadawca: " + this.nadawca + "\n" +
                "zabezpieczenia: " + this.zabezpieczenia + "\n" +
                "waga: " + this.wagaBrutto;
    }
}
