package project;

import java.util.HashMap;

public class Station
implements Comparable<Station>{

    private final String name;
    private final int index;
    private static int counter = 0;
    private final HashMap<Integer, Boolean> platforms = new HashMap<>();

    public Station(String name){
        this.name = name;
        this.index = counter++;
    }

    public void addPlatform(int number, boolean change){
        platforms.put(number, change);
    }

    public HashMap<Integer, Boolean> getPlatforms() {
        return platforms;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
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
