package edu.gatech;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {

    /** Vehicle ID */
    protected int ID;
    /** Vehicle Route */
    protected int route; //
    /** Vehicle Next Location */ //index of VehicleStop on VehilcleRoute.stopsOnRoute where the Vehicle is heading
    protected int nextLocation;
    /** Vehicle Previous Location */  //index of VehicleStop on VehilcleRoute.stopsOnRoute where the Vehicle just left
    protected int prevLocation;
    /** Vehicle Passengers */
    protected List<Rider> passengers = new ArrayList<>();
    /** Vehicle Capacity */
    protected int capacity;
    /** Vehicle overloading capacity*/
    protected  int overloadCap; //TODORUSHABH: overloadCap value has to be instantiated while creating object
    /** Vehicle Speed */
    protected int speed; // given in statute miles per hour
    protected int simulationTime;




    public void setRoute(int inputRoute) { this.route = inputRoute; }

    public void setLocation(int inputLocation) {
        this.prevLocation = this.nextLocation;
        this.nextLocation = inputLocation;
    }

    public  void setSimulationTime(int time){
        simulationTime = time;
    }

    public int getSimulationTime(){
        return simulationTime;
    }

    public void setPassengers(List<Rider> inputPassengers) { this.passengers = inputPassengers; }

    public void setCapacity(int inputCapacity) { this.capacity = inputCapacity; }

    public void setSpeed(int inputSpeed) { this.speed = inputSpeed; }

    public Integer getID() { return this.ID; }

    public Integer getRouteID() { return this.route; }

    public Integer getLocation() { return this.nextLocation; }

    public Integer getPastLocation() { return this.prevLocation; }

    public List<Rider> getPassengers() { return this.passengers; }
    public int getNumberOfPassengers() { return this.passengers.size(); }

    public int getCapacity() { return this.capacity; }

    public int getSpeed() { return this.speed; }


    public void displayEvent() {
        System.out.println(" " + this.getClass().getName().toLowerCase()+": " + Integer.toString(this.ID));
    }

    public void displayInternalStatus() {
        System.out.print(" " + this.getClass().getName().toLowerCase()+": "+"- ID: " + Integer.toString(ID) + " route: " + Integer.toString(route));
        System.out.print(" location from: " + Integer.toString(prevLocation) + " to: " + Integer.toString(nextLocation));
        System.out.print(" passengers: " + passengers.size() + " capacity: " + Integer.toString(capacity));
        System.out.println(" speed: " + Integer.toString(speed));
        System.out.println("No. of Passengers: "+Integer.toString(passengers.size()));
    }

    /*
    public void takeTurn() {
        System.out.println("drop off passengers - pickup passengers to capacity - move to next stop");
    }
    */

    /* //the entire process of removing passengers from the vehicle and putting new passengers will be taken care
        //by VehicleStop.exchangeRider() operation. We won't need adjustPassenger() operation
    public void adjustPassengers(List<Rider> gettingOn, int stopID) { //signature change
        //removing all the passengers that has to getoff at the stop
        for(Rider item : passengers){
            if(item.getTargetStopID() == stopID ){
                passengers.remove(item);
               // item.destroyRiderObject(); //this will be uncommented after destrorRiderObject is implemented;
            }
        }
        for(Rider item: gettingOn){
            passengers.add(item);
        }
    }
    */

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Vehicle me = (Vehicle) object;
            if (this.ID == me.getID()) {
                result = true;
            }
        }
        return result;
    }
}

