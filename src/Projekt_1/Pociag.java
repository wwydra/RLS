package Projekt_1;

import Projekt_1.Wagony.Wagon;
import Projekt_1.Wyjatki.TooHeavyLoadException;
import Projekt_1.Wyjatki.TooManyCarriagesException;
import Projekt_1.Wyjatki.TooManyElectricCarriagesException;

import java.util.*;

public class Pociag
implements Comparable<Pociag>{

    protected Lokomotywa lokomotywa;
    public ArrayList<Wagon> wagony;
    protected int liczbaPodlWagon;
    protected int liczbaPodlPDSE;
    protected double uciag;
    public int numer;
    private static int counter = 1;
    protected ArrayList<Stacja> trasa;
    private int dystans;
    private TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji;

    public Pociag(Lokomotywa lokomotywa, TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji) {
        this.lokomotywa = lokomotywa;
        this.wagony = new ArrayList<>();
        this.numer = counter++;
        this.liczbaPodlWagon = 0;
        this.liczbaPodlPDSE = 0;
        this.uciag = 0;
        this.mapaStacji = mapaStacji;
    }
    public ArrayList<Stacja> getTrasa() {
        return trasa;
    }

    public Lokomotywa getLokomotywa() {
        return lokomotywa;
    }

    public int getDystans(){
        for (int i = 0; i < trasa.size() - 1; i++){
            dystans += mapaStacji.get(trasa.get(i)).get(trasa.get(i + 1));
        }
        return dystans;
    }

    public void dodajWagon(Wagon wagon) throws TooManyCarriagesException, TooManyElectricCarriagesException, TooHeavyLoadException {
        if (this.liczbaPodlWagon >= lokomotywa.maxLiczbaWagonow){
            throw new TooManyCarriagesException();
        }
        if (this.liczbaPodlPDSE >= lokomotywa.maxLiczbaPDSE){
            throw new TooManyElectricCarriagesException();
        }
        if (this.uciag >= lokomotywa.maxUciag){
            throw new TooHeavyLoadException();
        }

        wagony.add(wagon);
        this.liczbaPodlWagon++;
        this.uciag += wagon.getWagaBrutto();
        if (wagon.isPodlaczenie()){
            this.liczbaPodlPDSE++;
        }
    }

    public void usunWagon(String ladunek){
        for (Wagon wagon : wagony){
            if (wagon.getLadunek() != null && wagon.getLadunek().equals(ladunek)){
                wagony.remove(wagon);
                return;
            }
        }
    }

    public void ustalTrase(ArrayList<Stacja> trasa){
        this.trasa = trasa;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();

        sb.append("Pociag o numerze ").append(this.numer).append(": lokomotywa o numerze ").append(this.lokomotywa.numerIdentyfikacyjny).append("\n").append("podlaczone wagony: \n");
        sb.append("\n");
        for (Wagon wagon : wagony){
            if (wagon != null) {
                sb.append(wagon.getData()).append("\n").append("\n");
            }
        }
        sb.append("Stacja poczatkowa: ").append(this.lokomotywa.stacjaZrodlowa).append("\n").append("Stacja docelowa: ").append(this.lokomotywa.stacjaDocelowa).append("\n");
        if (this.trasa != null)
            sb.append("Dystans do pokonania: ").append(getDystans());
        else
            sb.append("Dystans do pokonania: ").append(0);

        return sb.toString();
    }

    @Override
    public int compareTo(Pociag o) {
        return Integer.compare(this.getDystans(), o.getDystans());
    }
}

