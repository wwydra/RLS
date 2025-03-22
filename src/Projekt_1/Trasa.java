package Projekt_1;

import java.util.*;

public class Trasa {

    private Stacja poczatkowa;
    private Stacja docelowa;
    private Stack<Stacja> sprawdzone; // stacje ktore zostaly sprawdzone w danym cyklu
    private ArrayList<Stacja> lista; // stacje znalezione, ktore sa na trasie
    private int dystans;

    public int getDystans() {
        return dystans;
    }

    public Stacja getPoczatkowa() {
        return poczatkowa;
    }

    public Stacja getDocelowa() {
        return docelowa;
    }

    public Trasa(Stacja poczatkowa, Stacja docelowa){
        this.poczatkowa = poczatkowa;
        this.docelowa = docelowa;
        this.sprawdzone = new Stack<>();
        this.lista = new ArrayList<>();
        this.dystans = 0;
    }

    public ArrayList<Stacja> wyznaczTrase(TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji){

        znajdzPolaczenia(poczatkowa, mapaStacji);

        return lista;
    }

    public void znajdzPolaczenia(Stacja stacja, TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji){

        sprawdzone.push(stacja);
        lista.add(stacja);
        HashMap<Stacja, Integer> polaczenia = mapaStacji.get(stacja);
        List<Stacja> losowaKolejnosc = new ArrayList<>(polaczenia.keySet());
        Collections.shuffle(losowaKolejnosc);

        if (losowaKolejnosc.contains(docelowa)){
            lista.add(docelowa);
            dystans += polaczenia.get(docelowa);
        }else {
            for (Stacja polaczonaStacja : losowaKolejnosc) {

                if (!sprawdzone.contains(polaczonaStacja)) {
                    dystans += polaczenia.get(polaczonaStacja);
                    znajdzPolaczenia(polaczonaStacja, mapaStacji);
                    if (lista.get(lista.size() - 1).equals(docelowa)) {
                        return;
                    }
                }

            }

            HashMap<Stacja, Integer> usun = mapaStacji.get(lista.get(lista.size() - 2));
            int dlugoscUsun = usun.get(stacja);
            dystans -= dlugoscUsun;
            lista.remove(lista.size() - 1);
        }
    }
}
