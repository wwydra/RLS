package project;

import java.util.*;

public class Route {

    private final Station starting;
    private final Station destination;
    private final Stack<Station> checked;
    private final ArrayList<Station> list;
    private int distance;

    public Route(Station starting, Station destination){
        this.starting = starting;
        this.destination = destination;
        this.checked = new Stack<>();
        this.list = new ArrayList<>();
        this.distance = 0;
    }

    public ArrayList<Station> markTheRoute(TreeMap<Station, HashMap<Station, Integer>> stationMap){

        findConnections(starting, stationMap);

        return list;
    }

    public void findConnections(Station station, TreeMap<Station, HashMap<Station, Integer>> stationMap){

        checked.push(station);
        list.add(station);
        HashMap<Station, Integer> connections = stationMap.get(station);
        List<Station> random = new ArrayList<>(connections.keySet());
        Collections.shuffle(random);

        if (random.contains(destination)){
            list.add(destination);
            distance += connections.get(destination);
        }else {
            for (Station connected : random) {
                if (!checked.contains(connected)) {
                    distance += connections.get(connected);
                    findConnections(connected, stationMap);
                    if (list.get(list.size() - 1).equals(destination)) {
                        return;
                    }
                }

            }

            HashMap<Station, Integer> delete = stationMap.get(list.get(list.size() - 2));
            int deleteLength = delete.get(station);
            distance -= deleteLength;
            list.remove(list.size() - 1);
        }
    }

    public int getDistance() {
        return distance;
    }

    public Station getStarting() {
        return starting;
    }

    public Station getDestination() {
        return destination;
    }
}
