package Projekt_1;

import Projekt_1.Wagony.*;
import Projekt_1.Wyjatki.TooHeavyLoadException;
import Projekt_1.Wyjatki.TooManyCarriagesException;
import Projekt_1.Wyjatki.TooManyElectricCarriagesException;
import Projekt_1.Wyjatki.TooManyPassengersException;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        //stacje
        ArrayList<Stacja> stacje = new ArrayList<>();
        TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji = new TreeMap<>();

        //tworzenie bazowych 100 stacji
        for (int i = 0; i < 100; i++){
            String nazwa = "Stacja " + i;
            Stacja stacja = new Stacja(nazwa);
            stacje.add(stacja);
            mapaStacji.put(stacja, new HashMap<>());
        }

        //tworzenie losowych polaczen miedzy stacjami z uwzglednieniem odleglosci w km
        for (int i = 0; i < 100; i++){
            HashMap<Stacja, Integer> polaczenieBezp = mapaStacji.get(stacje.get(i));
            int liczbaPolaczen = (int)(Math.random()*2 + 1);

            for (int j = 0; j < liczbaPolaczen; j++){
                int index = (int)(Math.random()*100);
                while (index == i || stacje.get(index) == stacje.get(i)) {
                    index = (int)(Math.random()*100);
                }
                int odleglosc = (int)(Math.random() * 60 + 1);
                if (!polaczenieBezp.containsKey(stacje.get(index))) {
                    polaczenieBezp.put(stacje.get(index), odleglosc);
                }
            }
            for (Stacja stacja : polaczenieBezp.keySet()){
                HashMap<Stacja, Integer> spr = mapaStacji.get(stacja);
                if (stacja != stacje.get(i)) {
                    if (!spr.containsKey(stacje.get(i))) {
                        int odleglosc = polaczenieBezp.get(stacja);
                        spr.put(stacje.get(i), odleglosc);
                    }
                }
            }
        }

        //pociagi
        ArrayList<Pociag> pociagi = new ArrayList<>();
        ArrayList<PociagThread> watkiPociagow = new ArrayList<>();

        //tworzenie 25 bazowych pociagow
        for (int i = 0; i < 25; i++) {
            String nazwa = "Lokomotywa " + i;
            int maxLiczbaWagonow = (int) (Math.random() * 15 + 10);
            double maxUciag = Math.random() * 1000000;
            int maxLiczbaPDSE = (int) (Math.random() * 10 + 6);
            double predkosc = Math.random() * 120;

            Lokomotywa lokomotywa = new Lokomotywa(nazwa, maxLiczbaWagonow, maxUciag, maxLiczbaPDSE, predkosc);
            lokomotywa.ustalStacjeMacierzysta(stacje.get(i));
            Pociag pociag = new Pociag(lokomotywa, mapaStacji);
            lokomotywa.setPociag(pociag);

            //podlaczanie wagonow
            int liczbaWagonow = (int) (Math.random() * 6 + 5);
            for (int j = 0; j < liczbaWagonow; j++) {
                int typ = (int) (Math.random() * 12);
                switch (typ) {
                    case 0 -> {
                        WagonPasazerski wagonPasazerski = new WagonPasazerski((int) (Math.random() * 100), "przeciwpozarowe", Math.random() * 1000);
                        wagonPasazerski.podlacz();
                        try {
                            wagonPasazerski.zaladuj((int) (Math.random() * 50), Math.random() * 100);
                        } catch (TooManyPassengersException e) {
                            e.printStackTrace();
                        }
                        try {
                            pociag.dodajWagon(wagonPasazerski);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 1 -> {
                        WagonPocztowy wagonPocztowy = new WagonPocztowy("Poczta", "przeciwpozarowe", Math.random() * 100);
                        wagonPocztowy.podlacz();
                        wagonPocztowy.zaladuj("listy", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonPocztowy);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2 -> {
                        WagonRestauracyjny wagonRestauracyjny = new WagonRestauracyjny("PGHM", "wewnetrzne", Math.random() * 100);
                        wagonRestauracyjny.podlacz();
                        wagonRestauracyjny.zaladuj("artykuly spozywcze", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonRestauracyjny);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        WagonTowCiezki wagonTowCiezki = new WagonTowCiezki("PSH", "tasmy", Math.random() * 100);
                        wagonTowCiezki.zaladuj("wegiel", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonTowCiezki);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        WagonTowPodst wagonTowPodst = new WagonTowPodst("TMI", "sciany dzialowe", Math.random() * 100);
                        wagonTowPodst.zaladuj("zwir", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonTowPodst);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        WagonMatWybuch wagonMatWybuch = new WagonMatWybuch("TNT", "przeciwpozarowe", Math.random() * 100);
                        wagonMatWybuch.zaladuj("dynamit", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonMatWybuch);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 6 -> {
                        WagonMatToks wagonMatToks = new WagonMatToks("TOX", "ADR", Math.random() * 100);
                        wagonMatToks.zaladuj("amoniak", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonMatToks);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 7 -> {
                        WagonMatGazowe wagonMatGazowe = new WagonMatGazowe("GAZ", "przeciwpozarowe", Math.random() * 100);
                        wagonMatGazowe.zaladuj("azot", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonMatGazowe);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 8 -> {
                        WagonChlodniczy wagonChlodniczy = new WagonChlodniczy("COLD", "tasmy", Math.random() * 100);
                        wagonChlodniczy.podlacz();
                        wagonChlodniczy.zaladuj("mrozona zywnosc", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonChlodniczy);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 9 -> {
                        WagonBagazowoPocztowy wagonBagazowoPocztowy = new WagonBagazowoPocztowy("BAG", "DRT", Math.random() * 100);
                        wagonBagazowoPocztowy.podlacz();
                        wagonBagazowoPocztowy.zaladuj("paczki", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonBagazowoPocztowy);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 10 -> {
                        WagonCiekleMatToks wagonCiekleMatToks = new WagonCiekleMatToks("CMT", "przeciwpozarowe", Math.random() * 100);
                        wagonCiekleMatToks.zaladuj("benzyna", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonCiekleMatToks);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                    case 11 -> {
                        WagonMatCiekle wagonMatCiekle = new WagonMatCiekle("CI", "uszczelniony", Math.random() * 100);
                        wagonMatCiekle.zaladuj("woda", Math.random() * 1000);
                        try {
                            pociag.dodajWagon(wagonMatCiekle);
                        } catch (TooManyCarriagesException | TooHeavyLoadException |
                                 TooManyElectricCarriagesException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            //wyznaczanie losowej trasy
            int stacjaPoczatkowa = (int)(Math.random()*100);
            int stacjaDocelowa = (int)(Math.random()*100);
            while (stacjaPoczatkowa == stacjaDocelowa){
                stacjaPoczatkowa = (int)(Math.random()*100);
            }
            Trasa trasa = new Trasa(stacje.get(stacjaPoczatkowa), stacje.get(stacjaDocelowa));
            pociag.ustalTrase(trasa.wyznaczTrase(mapaStacji));

            pociag.lokomotywa.ustalStacjeZrodlowa(stacje.get(stacjaPoczatkowa));
            pociag.lokomotywa.ustalStacjeDocelowa(stacje.get(stacjaDocelowa));

            pociagi.add(pociag);
            PociagThread pociagThread = new PociagThread(pociag, mapaStacji);
            watkiPociagow.add(pociagThread);
        }

        KontrolaRuchu kontrolaRuchu = new KontrolaRuchu(watkiPociagow);
        kontrolaRuchu.uruchom();

        AppStateThread appStateThread = new AppStateThread(pociagi, "C:\\Users\\roman\\IdeaProjects\\GUI\\src\\Projekt_1\\Tekst\\AppState.txt");
        appStateThread.start();

        //menu
        System.out.println("Domyslnie do dyspozycji: \n" +
                " - stacje o numerach 1 - 100 \n" +
                " - pociagi o numerach 1 - 25");
        System.out.println("===================================");
        System.out.println("Wybierz opcje:" + "\n" +
                "- aby ustalic trase pociagu: T" + "\n" +
                "- aby wyswietlic raport o pociagu: R" + "\n" +
                "- aby stworzyc pociag: P" + "\n" +
                "- aby stworzyc wagon: W" + "\n" +
                "- aby stworzyc stacje: S" + "\n" +
                "- aby usunac obiekt: U" + "\n" + "\n" +
                "- aby zakonczyc dzialanie programu: Q");
        System.out.println("===================================");

        Scanner scanner = new Scanner(System.in);
        char opcja = scanner.next().charAt(0);

        while (opcja != 'Q'){
            switch (opcja){
                case 'R' -> {
                    System.out.println("Wybierz numer pociagu.");
                    int numerPociagu = scanner.nextInt();
                    System.out.println(pociagi.get(numerPociagu - 1).print());
                    System.out.println();
                    System.out.println("Trasa pociagu:");
                    ArrayList<Stacja> aktualnaTrasa = pociagi.get(numerPociagu - 1).getTrasa();
                    if (!aktualnaTrasa.isEmpty() && aktualnaTrasa != null) {
                        for (int i = 0; i < aktualnaTrasa.size(); i++) {
                            System.out.println(aktualnaTrasa.get(i));
                        }
                        System.out.println("Procent unkonczonej trasy: " + watkiPociagow.get(numerPociagu - 1).getProcentTrasy() + "%");
                        System.out.println("Aktualna stacja: " +
                                watkiPociagow.get(numerPociagu - 1).getAktualnaStacja().getNazwa());
                        System.out.println("Nastepna stacja: " +
                                watkiPociagow.get(numerPociagu - 1).getNastepnaStacja().getNazwa());
                        System.out.println("Procent ukonczonej trasy miedzy najblizszymi stacjami: " + watkiPociagow.get(numerPociagu - 1).getProcentAktuTrasy() + "%");
                    }
                }
                case 'T' -> {
                    System.out.println("Ustalanie trasy dla pociagu o numerze: ");
                    int numerPociagu = scanner.nextInt();
                    System.out.println("Ustal numer stacji poczatkowej.");
                    int numerStacjiPoczatkowej = scanner.nextInt();
                    System.out.println("Ustal numer stacji docelowej.");
                    int numerStacjiDocelowej = scanner.nextInt();

                    pociagi.get(numerPociagu - 1).lokomotywa.ustalStacjeZrodlowa(stacje.get(numerStacjiPoczatkowej));
                    pociagi.get(numerPociagu - 1).lokomotywa.ustalStacjeDocelowa(stacje.get(numerStacjiDocelowej));

                    Trasa trasa = new Trasa(pociagi.get(numerPociagu - 1).lokomotywa.stacjaZrodlowa, pociagi.get(numerPociagu - 1).lokomotywa.stacjaDocelowa);
                    pociagi.get(numerPociagu - 1).ustalTrase(trasa.wyznaczTrase(mapaStacji));

                    System.out.println("Trasa zostala wyznaczona.");
                }
                case 'P' -> {
                    //tworzenie nowego pociagu bez wagonow
                    System.out.println("Podaj nazwe lokomotywy.");
                    String nazwa = scanner.next();
                    System.out.println("Ustal maksymalna liczbe wagonow.");
                    int maxLiczbaWagonow = scanner.nextInt();
                    System.out.println("Ustal maksymalny uciag.");
                    double maxUciag = scanner.nextDouble();
                    System.out.println("Ustal maksymalna liczbe wagonow z PDSE.");
                    int maxLiczbaPDSE = scanner.nextInt();
                    System.out.println("Ustal predkosc.");
                    double predkosc = scanner.nextDouble();

                    Lokomotywa l = new Lokomotywa(nazwa, maxLiczbaWagonow, maxUciag, maxLiczbaPDSE, predkosc);
                    Pociag pociag = new Pociag(l, mapaStacji);
                    l.setPociag(pociag);

                    System.out.println("Ustal numer stacji poczatkowej.");
                    int numerStacjiPoczatkowej = scanner.nextInt();
                    System.out.println("Ustal numer stacji docelowej.");
                    int numerStacjiDocelowej = scanner.nextInt();
                    l.ustalStacjeZrodlowa(stacje.get(numerStacjiPoczatkowej));
                    l.ustalStacjeDocelowa(stacje.get(numerStacjiDocelowej));
                    Trasa trasa = new Trasa(pociag.lokomotywa.stacjaZrodlowa, pociag.lokomotywa.stacjaDocelowa);
                    pociag.ustalTrase(trasa.wyznaczTrase(mapaStacji));

                    PociagThread pociagThread = new PociagThread(pociag, mapaStacji);
                    pociagThread.start();
                    kontrolaRuchu.dodajWatek(pociagThread);
                    pociagi.add(pociag);
                    appStateThread.dodajPociag(pociag);

                    System.out.println("Pociag o numerze " + pociag.numer + " zostal utworzony.");
                }
                case 'W' -> {
                    //tworzenie nowego obiektu Wagon
                    System.out.println("Jaki wagon chcesz stworzyc?" + "\n" +
                            " - wagon pocztowy: PO" + "\n" +
                            " - wagon bagaazowo-pocztowy: BP" + "\n" +
                            " - wagon chlodniczy: CH" + "\n" +
                            " - wagon na ciekle materialy toksyczne: CMT" + "\n" +
                            " - wagon na materialy ciekle: MC" + "\n" +
                            " - wagon na materialy gazowe: MG" + "\n" +
                            " - wagon na materialy toksyczne: MT" + "\n" +
                            " - wagon na materialy wybuchowe: MW" + "\n" +
                            " - wagon pasazerski: PA" + "\n" +
                            " - wagon restauracyjny: R" + "\n" +
                            " - wagon towarowy podstawowy: TP" + "\n" +
                            " - wagon towarowy ciezki : TC" + "\n");
                    String typWagon = scanner.next();
                    boolean done = false;

                    while (!done) {
                        switch (typWagon) {
                            case "BP" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonBagazowoPocztowy wagonBagazowoPocztowy = new WagonBagazowoPocztowy(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonBagazowoPocztowy.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonBagazowoPocztowy);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "CH" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonChlodniczy wagonChlodniczy = new WagonChlodniczy(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonChlodniczy.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonChlodniczy);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "CMT" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonCiekleMatToks wagonCiekleMatToks = new WagonCiekleMatToks(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonCiekleMatToks.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonCiekleMatToks);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MC" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonMatCiekle wagonMatCiekle = new WagonMatCiekle(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonMatCiekle.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonMatCiekle);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MG" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonMatGazowe wagonMatGazowe = new WagonMatGazowe(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonMatGazowe.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonMatGazowe);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MT" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonMatToks wagonMatToks = new WagonMatToks(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonMatToks.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonMatToks);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "MW" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonMatWybuch wagonMatWybuch = new WagonMatWybuch(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonMatWybuch.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonMatWybuch);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "PA" -> {
                                System.out.println("Podaj liczbe miejsc.");
                                int liczbaMiejsc = scanner.nextInt();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonPasazerski wagonPasazerski = new WagonPasazerski(liczbaMiejsc,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Ile osob wsiadzie do wagonu?");
                                int liczbaOsob = scanner.nextInt();
                                System.out.println("Jaka jest waga?");
                                double wagaLadunku = scanner.nextDouble();
                                try {
                                    wagonPasazerski.zaladuj(liczbaOsob, wagaLadunku);
                                }catch (TooManyPassengersException e){
                                    e.printStackTrace();
                                }

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonPasazerski);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "R" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonRestauracyjny wagonRestauracyjny = new WagonRestauracyjny(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonRestauracyjny.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonRestauracyjny);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "TP" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonTowPodst wagonTowPodst = new WagonTowPodst(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonTowPodst.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonTowPodst);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "TC" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonTowCiezki wagonTowCiezki = new WagonTowCiezki(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonTowCiezki.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonTowCiezki);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            case "PO" -> {
                                System.out.println("Podaj nadawce.");
                                String nadawca = scanner.next();
                                System.out.println("Jakie sa zabezpieczenia?");
                                String zabezpieczenia = scanner.next();
                                System.out.println("Podaj wage bez ladunku.");
                                double waga = scanner.nextDouble();

                                WagonPocztowy wagonPocztowy = new WagonPocztowy(nadawca,zabezpieczenia, waga);

                                //zaladowanie wagonu
                                System.out.println("Co zaladowac do wagonu?");
                                String ladunek = scanner.next();
                                System.out.println("Jaka jest waga ladunku?");
                                double wagaLadunku = scanner.nextDouble();
                                wagonPocztowy.zaladuj(ladunek, wagaLadunku);

                                //przypisanie wagonu do pociagu
                                System.out.println("Do pociagu o jakim numerze chcesz przypisac ten wagon?");
                                int numerPociagu = scanner.nextInt();
                                try {
                                    pociagi.get(numerPociagu - 1).dodajWagon(wagonPocztowy);
                                }catch (TooManyCarriagesException | TooHeavyLoadException | TooManyElectricCarriagesException e){
                                    e.printStackTrace();
                                }

                                done = true;
                            }
                            default -> {
                                System.out.println("Wybierz wagon z listy.");
                                typWagon = scanner.next();
                            }
                        }
                    }
                }
                case 'S' -> {
                    //tworzenie nowego obiektu Stacja i tworzenie polaczen
                    System.out.println("Podaj nazwe stacji.");
                    String nazwa = scanner.next();

                    Stacja stacja = new Stacja(nazwa);

                    System.out.println("Z iloma stacjami ma byc polaczona?");
                    int num = scanner.nextInt();
                    int countStacje = 0;
                    HashMap<Stacja, Integer> polaczenieBezp = new HashMap<>();

                    while (countStacje < num){
                        System.out.println("Podaj numer stacji do polaczenia.");
                        int numer = scanner.nextInt();
                        Stacja s = stacje.get(numer);
                        System.out.println("Podaj odleglosc.");
                        int odleglosc = scanner.nextInt();
                        polaczenieBezp.put(s, odleglosc);
                        HashMap<Stacja, Integer> drugaStrona = mapaStacji.get(s);
                        drugaStrona.put(stacja, odleglosc);

                        countStacje++;
                    }

                    mapaStacji.put(stacja, polaczenieBezp);
                    stacje.add(stacja);

                    System.out.println("Stacja " + nazwa + "o numerze " + stacja.getIndex() + " zostala utworzona.");
                }
                case 'U' -> {
                    System.out.println("Jaki obiekt chcesz usunac?" + "\n" +
                            " - stacje: S" + "\n" +
                            " - pociag: P" + "\n" +
                            " - wagon: W");
                    String usun = scanner.next();
                    boolean done = false;

                    while (!done){
                        switch (usun) {
                            case "S" -> {
                                System.out.println("Podaj numer stacji, ktora chcesz usunac.");
                                int numerStacji = scanner.nextInt();
                                mapaStacji.remove(stacje.get(numerStacji));
                                stacje.remove(numerStacji);

                                System.out.println("Stacja o numerze " + numerStacji + " zostala usunieta.");
                                System.out.println("Tym samym stacje o numerach wyzszych beda indeksowane numerami o 1 mniejszymi niz poprzednio.");
                                System.out.println("Wybierajac numer stacji o numerze wyzszym niz stacja wlasnie usunieta, wpisz numer zmniejszony o 1 od poprzednio wpisywanego.");
                                done = true;
                            }
                            case "P" -> {
                                System.out.println("Podaj numer pociagu do usuniecia.");
                                int numerPociagu = scanner.nextInt();
                                appStateThread.usunPociag(pociagi.get(numerPociagu - 1));
                                pociagi.remove(numerPociagu - 1);
                                watkiPociagow.get(numerPociagu - 1).interrupt();

                                System.out.println("Pociag zostal usuniety i tym samym pociagi o numerach wyzszych beda indeksowane numerami o 1 mniejszymi niz poprzednio.");
                                System.out.println("Wybierajac numer pociagu o numerze wyzszym niz pociag wlasnie usuniety, wpisz numer zmniejszony o 1 od poprzednio wpisywanego.");
                                done = true;
                            }
                            case "W" -> {
                                System.out.println("Wybierz numer pociagu.");
                                int numerPociagu = scanner.nextInt();
                                System.out.println("Wagon z jakim ladunkiem chcesz usunac?");
                                String ladunek = scanner.next();
                                pociagi.get(numerPociagu - 1).usunWagon(ladunek);

                                System.out.println("Wagon z ladunkiem " + ladunek + " zostal usuniety.");
                                done = true;
                            }
                            default -> {
                                System.out.println("Wybierz jedna z opcji.");
                                usun = scanner.next();
                            }
                        }
                    }

                }
                default -> System.out.println("Wybierz jedna z opcji podanych w instrukcji.");
            }
            System.out.println("===================================");
            System.out.println("Wybierz opcje:" + "\n" +
                    "- aby ustalic trase pociagu: T" + "\n" +
                    "- aby wyswietlic raport o pociagu: R" + "\n" +
                    "- aby stworzyc pociag: P" + "\n" +
                    "- aby stworzyc wagon: W" + "\n" +
                    "- aby stworzyc stacje: S" + "\n" +
                    "- aby usunac obiekt: U" + "\n" + "\n" +
                    "- aby zakonczyc dzialanie programu: Q");
            System.out.println("===================================");
            opcja = scanner.next().charAt(0);
        }

        kontrolaRuchu.zakoncz();
        appStateThread.interrupt();
    }
}
