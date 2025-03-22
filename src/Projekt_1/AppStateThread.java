package Projekt_1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AppStateThread
        extends Thread{

    private ArrayList<Pociag> sortowanePociagi;
    private String sciezkaPliku;
    private final Object object = new Object();

    public AppStateThread(ArrayList<Pociag> pociagi, String sciezkaPliku){
        this.sciezkaPliku = sciezkaPliku;
        this.sortowanePociagi = new ArrayList<>(pociagi);
    }

    public void dodajPociag(Pociag pociag){
        this.sortowanePociagi.add(pociag);
    }

    public void usunPociag(Pociag pociag){
        this.sortowanePociagi.remove(pociag);
    }

    public void sortuj(){
        Collections.sort(sortowanePociagi);
        Collections.reverse(sortowanePociagi);
        for (Pociag pociag: sortowanePociagi){
            Collections.sort(pociag.wagony);
        }
    }

    public void zapisz(){
        try {
            FileWriter fileWriter = new FileWriter(sciezkaPliku);

            fileWriter.write("---Zestawienie aktualnych skladow pociagow---\n\n");
            for (Pociag pociag : sortowanePociagi){
                fileWriter.write(pociag.print());
                fileWriter.write("\n--------------------------------------------------\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try{
                Thread.sleep(5000);
                synchronized (object) {
                    sortuj();
                    zapisz();
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
