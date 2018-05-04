package edu.gatech;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Vehicle {

    public Bus(int uniqueValue) {
        this.ID = uniqueValue;
        this.route = -1;
        this.nextLocation = -1;
        this.prevLocation = -1;
        this.passengers = new ArrayList<>();
        this.capacity = -1;
        this.overloadCap = -1;
        this.speed = -1;

    }

    //making Bus without passengers
    public Bus(int uniqueValue, int inputRoute, int inputLocation, int inputCapacity, int inputSpeed) {
        this.ID = uniqueValue;
        this.route = inputRoute;
        this.nextLocation = inputLocation;
        this.prevLocation = inputLocation;
        //this.passengers = inputPassengers;
        this.capacity = inputCapacity;
        this.overloadCap = (int)(inputCapacity * 1.2);
        this.speed = inputSpeed;
        this.simulationTime = 0;
    }

    //making bus with passengers
    public Bus(int uniqueValue, int inputRoute, int inputLocation, List<Rider> inputPassengers, int inputCapacity, int inputSpeed) {
        this.ID = uniqueValue;
        this.route = inputRoute;
        this.nextLocation = inputLocation;
        this.prevLocation = inputLocation;
        this.passengers = inputPassengers;
        this.capacity = inputCapacity;
        this.overloadCap = (int)(inputCapacity * 1.2);
        this.speed = inputSpeed;
        this.simulationTime =0;
    }

}
