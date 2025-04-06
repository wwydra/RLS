package project.exceptions;

import project.Train;

public class RailroadHazard
extends Exception{
    public RailroadHazard(Train train) {
        super("200 km/h exceeded.\n" + train.print());

    }

}
