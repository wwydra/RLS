package Projekt_1;

import Projekt_1.Wyjatki.RailroadHazard;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class KontrolaRuchu {

    private ArrayList<PociagThread> watkiPociagow;

    public KontrolaRuchu(ArrayList<PociagThread> watkiPociagow) {
        this.watkiPociagow = watkiPociagow;
    }

    public void dodajWatek(PociagThread pociagThread){
        this.watkiPociagow.add(pociagThread);
    }

    public void uruchom() {
        for (PociagThread watek : this.watkiPociagow) {
            watek.start();
        }
    }

    public void zakoncz(){
        for (PociagThread watek : this.watkiPociagow)
            watek.interrupt();
    }
}
class PociagThread
        extends Thread {
    private Pociag pociag;
    private ArrayList<Stacja> trasa;
    private TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji;
    private Stacja aktualnaStacja;
    private Stacja nastepnaStacja;
    private ReentrantLock lock;
    private int procentTrasy;
    private int procentAktuTrasy;

    public PociagThread(Pociag pociag, TreeMap<Stacja, HashMap<Stacja, Integer>> mapaStacji){
        this.pociag = pociag;
        this.trasa = pociag.trasa;
        this.mapaStacji = mapaStacji;
        this.lock = new ReentrantLock();
        this.procentTrasy = 0;
    }

    public Stacja getNastepnaStacja() {
        return nastepnaStacja;
    }

    public Stacja getAktualnaStacja() {
        return aktualnaStacja;
    }

    public synchronized int procentUkonczonejTrasy(Stacja aktualnaStacja, Pociag pociag, int dystans){
        int trasaCalkowita = 0;
        for (int i = 0; i < trasa.size() - 1; i++){
            trasaCalkowita += mapaStacji.get(trasa.get(i)).get(trasa.get(i + 1));
        }

        int przebytaTrasa = 0;
        for (int i = 0; i <= trasa.indexOf(aktualnaStacja) - 1; i++){
            przebytaTrasa += mapaStacji.get(trasa.get(i)).get(trasa.get(i + 1));
        }
        przebytaTrasa += dystans;

        return (int)((double)przebytaTrasa/trasaCalkowita * 100);
    }

    public synchronized int getProcentTrasy() {
        return procentTrasy;
    }

    public synchronized int getProcentAktuTrasy() {
        return procentAktuTrasy;
    }

    @Override
    public void run() {
        Thread predkosc = new Thread(() -> {
            while (!Thread.interrupted()){
                try {
                    pociag.lokomotywa.setPredkosc(pociag.lokomotywa.zmienPredkosc(pociag.lokomotywa.getPredkosc()));
                } catch (RailroadHazard e) {
                    e.printStackTrace();
                }
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    return;
                }
            }
        });
        predkosc.start();
        QueueThread kolejka = new QueueThread();
        kolejka.start();

        while (!Thread.interrupted()) {
            try {
                this.procentTrasy = 0;
                //iterowanie po stacjach na trasie pociagu
                for (int i = 0; i < trasa.size() - 1; i++) {
                    aktualnaStacja = trasa.get(i);
                    if (i + 1 < trasa.size())
                        nastepnaStacja = trasa.get(i + 1);

                    //postoj na stacji
                    Thread.sleep(2000);

                    //przemieszczenie pociagu miedzy aktualna stacja a nastepna
                    if (StacjeThread.sprawdzStan(aktualnaStacja, nastepnaStacja)) {
                        if (lock.tryLock()) {
                            try {
                                StacjeThread.wjedz(aktualnaStacja, nastepnaStacja);
                                int dystans = mapaStacji.get(aktualnaStacja).get(nastepnaStacja);
                                long czasPrzejazdu = Math.round((dystans / pociag.lokomotywa.getPredkosc()) * 3600);
                                this.procentAktuTrasy = 0;

                                for (int j = 0; j <= dystans; j++){
                                    czasPrzejazdu = Math.round((dystans / pociag.lokomotywa.getPredkosc()) * 3600);
                                    this.procentAktuTrasy = (int)((double)j/dystans*100);
                                    Thread.sleep(czasPrzejazdu/dystans * 50); //skrocony czas, aby pokazac dzialalnosc programu
                                }

                                this.procentTrasy = procentUkonczonejTrasy(aktualnaStacja, pociag, dystans);
                                StacjeThread.wyjedz(aktualnaStacja, nastepnaStacja);
                            } finally {
                                lock.unlock();
                            }
                        } else {
                            QueueThread.dodajDoKolejki(this);
                        }
                    }else{
                        StacjeThread.wjedz(aktualnaStacja, nastepnaStacja);
                        int dystans = mapaStacji.get(aktualnaStacja).get(nastepnaStacja);
                        long czasPrzejazdu = Math.round((dystans / pociag.lokomotywa.getPredkosc()) * 3600);
                        procentAktuTrasy = 0;

                        for (int j = 0; j <= dystans; j++){
                            procentAktuTrasy = (j/dystans)*100;
                            Thread.sleep(czasPrzejazdu/dystans * 50);
                        }

                        this.procentTrasy = procentUkonczonejTrasy(aktualnaStacja, pociag, dystans);
                        StacjeThread.wyjedz(aktualnaStacja, nastepnaStacja);
                    }
                }

                //postoj na stacji docelowej i wyznaczenie trasy powrotnej
                Thread.sleep(30000);
                Trasa nowaTrasa = new Trasa(trasa.get(trasa.size() - 1), trasa.get(0));
                trasa = nowaTrasa.wyznaczTrase(mapaStacji);

            } catch (InterruptedException e) {
                predkosc.interrupt();
                kolejka.interrupt();
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

class StacjeThread
    extends Thread{

    private static HashMap<HashMap<Stacja, Stacja>, Boolean> stacje = new HashMap<>();

    public static synchronized boolean sprawdzStan(Stacja aktualnaStacja, Stacja nastepnaStacja){
        HashMap<Stacja, Stacja> trasa = new HashMap<>();
        trasa.put(aktualnaStacja, nastepnaStacja);
        if (stacje.containsKey(trasa)){
            return stacje.get(trasa);
        }else{
            stacje.put(trasa, true);
            return true;
        }
    }

    public static synchronized void wjedz(Stacja aktualnaStacja, Stacja nastepnaStacja){
        HashMap<Stacja, Stacja> trasa = new HashMap<>();
        trasa.put(aktualnaStacja, nastepnaStacja);
        stacje.put(trasa, true);
    }

    public static synchronized void wyjedz(Stacja aktualnaStacja, Stacja nastepnaStacja){
        HashMap<Stacja, Stacja> trasa = new HashMap<>();
        trasa.put(aktualnaStacja, nastepnaStacja);
        stacje.put(trasa, false);
    }

    @Override
    public void run() {

    }
}

class QueueThread
    extends Thread {

    private static Queue<PociagThread> kolejka = new LinkedList<>();
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition notEmpty = lock.newCondition();

    public static void dodajDoKolejki(PociagThread pociagThread){
        lock.lock();
        try {
            kolejka.offer(pociagThread);
            while (kolejka.peek() != pociagThread) {
                notEmpty.await();
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void usunZKolejki(){
        lock.lock();
        try {
            kolejka.poll();
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            lock.lock();
            try {
                while (kolejka.isEmpty()) {
                    notEmpty.await();
                }
                kolejka.peek().start();
                if (kolejka.peek() != null)
                    kolejka.peek().join();
                usunZKolejki();

            }catch (InterruptedException e){
                return;
            } finally {
                lock.unlock();
            }
        }
    }
}
