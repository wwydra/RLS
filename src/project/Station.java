package project;

import java.util.HashMap;

public class Station
implements Comparable<Station>{

    private String name;
    private int index;
    private static int counter = 0;
    private HashMap<Integer, Boolean> platforms = new HashMap<>();

    public Station(String name){
        this.name = name;
        this.index = counter++;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void addPlatform(int number, boolean change){
        platforms.put(number, change);
    }

    public HashMap<Integer, Boolean> getPlatforms() {
        return platforms;
    }

    @Override
    public int compareTo(Station s) {
        return this.name.compareTo(s.getName());
    }

    @Override
    public String toString(){
        return name;
    }
}
