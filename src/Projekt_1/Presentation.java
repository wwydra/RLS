package Projekt_1;

import Projekt_1.Wagony.WagonChlodniczy;
import Projekt_1.Wagony.WagonPocztowy;
import Projekt_1.Wyjatki.TooHeavyLoadException;
import Projekt_1.Wyjatki.TooManyCarriagesException;
import Projekt_1.Wyjatki.TooManyElectricCarriagesException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Presentation {
    public static void main(String[] args) {

        ArrayList<Stacja> stacje = new ArrayList<>();
        TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji = new TreeMap<>();

        Stacja stacja1 = new Stacja("Stacja 1");
        Stacja stacja2 = new Stacja("Stacja 2");
        Stacja stacja3 = new Stacja("Stacja 3");
        stacje.add(stacja1);
        stacje.add(stacja2);
        stacje.add(stacja3);

        for (Stacja stacja : stacje){
            System.out.println("Nazwa stacji: " + stacja.getNazwa() + " index:" + stacja.getIndex());
        }
        System.out.println("=====================================================");

        HashMap<Stacja, Integer> polaczenia = new HashMap<>();
        polaczenia.put(stacja2, 45);
        polaczenia.put(stacja3, 23);
        mapaStacji.put(stacja1, polaczenia);

        for (Map.Entry<Stacja, HashMap<Stacja, Integer>> entry : mapaStacji.entrySet()){
            System.out.println(entry.getKey().getNazwa());
            HashMap<Stacja, Integer> polStacje = entry.getValue();
            System.out.println("Polaczenia bezposrednie:");
            for (Map.Entry<Stacja, Integer> pol : polStacje.entrySet()){
                System.out.println(pol.getKey().getNazwa() + ", dystans: " + pol.getValue());
            }

        }
        System.out.println("=====================================================");

        Lokomotywa lokomotywa = new Lokomotywa("ADE", 45, 12000, 18, 120);
        lokomotywa.ustalStacjeMacierzysta(stacja1);

        Pociag pociag = new Pociag(lokomotywa, mapaStacji);
        pociag.lokomotywa.ustalStacjeZrodlowa(stacja1);
        pociag.lokomotywa.ustalStacjeDocelowa(stacja3);

        Trasa trasa = new Trasa(pociag.lokomotywa.stacjaZrodlowa, pociag.lokomotywa.stacjaDocelowa);
        pociag.ustalTrase(trasa.wyznaczTrase(mapaStacji));
        System.out.println(pociag.print());
        System.out.println("=====================================================");

        WagonPocztowy wagon1 = new WagonPocztowy("POST", "alarm", 340);
        wagon1.zaladuj("poczta", 500);
        System.out.println(wagon1.getData());
        System.out.println("=====================================================");

        WagonChlodniczy wagon2 = new WagonChlodniczy("FRE", "termiczne", 500);
        wagon2.zaladuj("mrozona zywnosc", 600);

        wagon2.podlacz();
        wagon1.podlacz();
        try {
            pociag.dodajWagon(wagon1);
            pociag.dodajWagon(wagon2);
        }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
            e.printStackTrace();
        }
        System.out.println(pociag.print());
        System.out.println("=====================================================");

        pociag.usunWagon("poczta");
        System.out.println(pociag.print());
        System.out.println("=====================================================");

        PociagThread pociagThread = new PociagThread(pociag, mapaStacji);
        pociagThread.start();

        System.out.println("Procent unkonczonej trasy: " + pociagThread.getProcentTrasy() + "%");
        System.out.println("Aktualna stacja: " +
                pociagThread.getAktualnaStacja().getNazwa());
        System.out.println("Nastepna stacja: " +
                pociagThread.getNastepnaStacja().getNazwa());
        System.out.println("Procent ukonczonej trasy miedzy najblizszymi stacjami: " + pociagThread.getProcentAktuTrasy() + "%");
        System.out.println("=====================================================");






        pociagThread.interrupt();
    }
}
