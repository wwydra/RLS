package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AppStateThread
        extends Thread{

    private final ArrayList<Train> sortedTrains;
    private final String filePath;
    private final Object object = new Object();

    public AppStateThread(ArrayList<Train> trains, String filePath){
        this.filePath = filePath;
        this.sortedTrains = new ArrayList<>(trains);
    }

    public void addTrain(Train train){
        this.sortedTrains.add(train);
    }

    public void deleteTrain(Train train){
        this.sortedTrains.remove(train);
    }

    public void sort(){
        Collections.sort(sortedTrains);
        Collections.reverse(sortedTrains);
        for (Train train: sortedTrains){
            Collections.sort(train.carriages);
        }
    }

    public void save(){
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("---List of current train compositions---\n\n");
            for (Train train : sortedTrains){
                fileWriter.write(train.print());
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
                    sort();
                    save();
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
