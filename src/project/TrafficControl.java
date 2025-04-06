package project;

import project.exceptions.RailroadHazard;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficControl {

    private ArrayList<TrainThread> trainThreads;

    public TrafficControl(ArrayList<TrainThread> trainThreads) {
        this.trainThreads = trainThreads;
    }

    public void addThread(TrainThread trainThread){
        this.trainThreads.add(trainThread);
    }

    public void start() {
        for (TrainThread thread : this.trainThreads) {
            thread.start();
        }
    }

    public void finish(){
        for (TrainThread thread : this.trainThreads)
            thread.interrupt();
    }
}
class TrainThread
        extends Thread {
    private Train train;
    private ArrayList<Station> route;
    private TreeMap<Station, HashMap<Station, Integer>> stationMap;
    private Station currentStation;
    private Station nextStation;
    private ReentrantLock lock;
    private int routePercent;
    private int currRoutePercent;

    public TrainThread(Train train, TreeMap<Station, HashMap<Station, Integer>> stationMap){
        this.train = train;
        this.route = train.route;
        this.stationMap = stationMap;
        this.lock = new ReentrantLock();
        this.routePercent = 0;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public synchronized int percentOfRouteCompleted(Station currentStation, Train train, int distance){
        int totalRoute = 0;
        for (int i = 0; i < route.size() - 1; i++){
            totalRoute += stationMap.get(route.get(i)).get(route.get(i + 1));
        }

        int traveledRoute = 0;
        for (int i = 0; i <= route.indexOf(currentStation) - 1; i++){
            traveledRoute += stationMap.get(route.get(i)).get(route.get(i + 1));
        }
        traveledRoute += distance;

        return (int)((double)traveledRoute/totalRoute * 100);
    }

    public synchronized int getRoutePercent() {
        return routePercent;
    }

    public synchronized int getCurrRoutePercent() {
        return currRoutePercent;
    }

    @Override
    public void run() {
        Thread speed = new Thread(() -> {
            while (!Thread.interrupted()){
                try {
                    train.locomotive.setSpeed(train.locomotive.changeSpeed(train.locomotive.getSpeed()));
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
        speed.start();
        QueueThread queueThread = new QueueThread();
        queueThread.start();

        while (!Thread.interrupted()) {
            try {
                this.routePercent = 0;
                for (int i = 0; i < route.size() - 1; i++) {
                    currentStation = route.get(i);
                    if (i + 1 < route.size())
                        nextStation = route.get(i + 1);

                    Thread.sleep(2000);

                    if (StationsThread.checkState(currentStation, nextStation)) {
                        if (lock.tryLock()) {
                            try {
                                StationsThread.driveIn(currentStation, nextStation);
                                int distance = stationMap.get(currentStation).get(nextStation);
                                long travelTime = Math.round((distance / train.locomotive.getSpeed()) * 3600);
                                this.currRoutePercent = 0;

                                for (int j = 0; j <= distance; j++){
                                    travelTime = Math.round((distance / train.locomotive.getSpeed()) * 3600);
                                    this.currRoutePercent = (int)((double)j/distance*100);
                                    Thread.sleep(travelTime/distance * 50);
                                }

                                this.routePercent = percentOfRouteCompleted(currentStation, train, distance);
                                StationsThread.driveOut(currentStation, nextStation);
                            } finally {
                                lock.unlock();
                            }
                        } else {
                            QueueThread.addToQueue(this);
                        }
                    }else{
                        StationsThread.driveIn(currentStation, nextStation);
                        int distance = stationMap.get(currentStation).get(nextStation);
                        long travelTime = Math.round((distance / train.locomotive.getSpeed()) * 3600);
                        currRoutePercent = 0;

                        for (int j = 0; j <= distance; j++){
                            currRoutePercent = (j/distance)*100;
                            Thread.sleep(travelTime/distance * 50);
                        }

                        this.routePercent = percentOfRouteCompleted(currentStation, train, distance);
                        StationsThread.driveOut(currentStation, nextStation);
                    }
                }

                Thread.sleep(30000);
                Route newRoute = new Route(route.get(route.size() - 1), route.get(0));
                route = newRoute.markTheRoute(stationMap);

            } catch (InterruptedException e) {
                speed.interrupt();
                queueThread.interrupt();
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

class StationsThread
    extends Thread{

    private static HashMap<HashMap<Station, Station>, Boolean> stations = new HashMap<>();

    public static synchronized boolean checkState(Station currStation, Station nextStation){
        HashMap<Station, Station> route = new HashMap<>();
        route.put(currStation, nextStation);
        if (stations.containsKey(route)){
            return stations.get(route);
        }else{
            stations.put(route, true);
            return true;
        }
    }

    public static synchronized void driveIn(Station currStation, Station nextStation){
        HashMap<Station, Station> route = new HashMap<>();
        route.put(currStation, nextStation);
        stations.put(route, true);
    }

    public static synchronized void driveOut(Station currStation, Station nextStation){
        HashMap<Station, Station> route = new HashMap<>();
        route.put(currStation, nextStation);
        stations.put(route, false);
    }

    @Override
    public void run() {

    }
}

class QueueThread
    extends Thread {

    private static Queue<TrainThread> queue = new LinkedList<>();
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition notEmpty = lock.newCondition();

    public static void addToQueue(TrainThread trainThread){
        lock.lock();
        try {
            queue.offer(trainThread);
            while (queue.peek() != trainThread) {
                notEmpty.await();
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void removeFromQueue(){
        lock.lock();
        try {
            queue.poll();
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
                while (queue.isEmpty()) {
                    notEmpty.await();
                }
                queue.peek().start();
                if (queue.peek() != null)
                    queue.peek().join();
                removeFromQueue();

            }catch (InterruptedException e){
                return;
            } finally {
                lock.unlock();
            }
        }
    }
}
