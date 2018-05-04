package edu.gatech;

import java.util.HashMap;

public class TrainRoute extends VehicleRoute {

    public TrainRoute(int uniqueID, int inputNumber, String inputName) {
        this.ID = uniqueID;
        this.routeNumber = inputNumber;
        this.routeName = inputName;
        this.stopsOnRoute = new HashMap<Integer, int[]>();
    }
}
