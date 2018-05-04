package edu.gatech;

import java.util.HashMap;

public abstract class VehicleRoute {

    protected int ID;
    protected int routeNumber;
    protected String routeName;
    protected HashMap<Integer, int[]> stopsOnRoute = new HashMap<>(); //in hasmap value (int[]), int[0] = stopID, int[1] = avgtraveltime between stops, int[2] = travelTimeFactor

    public VehicleRoute() {
        this.ID = -1;
    }

    //RB : VehicleRoute is abstract class, we won't need constructor
    /*
    public VehicleRoute(int uniqueValue) {
        this.ID = uniqueValue;
        this.routeNumber = -1;
        this.routeName = "";
        this.stopsOnRoute = new HashMap<Integer, Integer>();
    }
    */

    //RB : VehicleRoute is abstract class, we won't need constructor
    /*
    public VehicleRoute(int uniqueValue, int inputNumber, String inputName) {
        this.ID = uniqueValue;
        this.routeNumber = inputNumber;
        this.routeName = inputName;
        this.stopsOnRoute = new HashMap<Integer, Integer>();
   }
   */
    public void changeTravelTimeFactor(int factor, int stopID){
        for(int[] intArray: stopsOnRoute.values()){
            if(intArray[0] == stopID){
                intArray[2] = factor;
            }
        }
    }

    public void setNumber(int inputNumber) { this.routeNumber = inputNumber; }

    public void setName(String inputName) { this.routeName = inputName; }

    /** This method is modified becuase signature of stopsOnRoute is changed*/
    public void addNewStop(int stopID, int travelTime){
        int[] tempArray = new int[3];
        tempArray[0] = stopID;
        tempArray[1] = travelTime;
        tempArray[2] = 100;
        this.stopsOnRoute.put(stopsOnRoute.size(), tempArray);
    }

    public Integer getID() { return this.ID; }

    public Integer getNumber() { return this.routeNumber; }

    public String getName() { return this.routeName; }

    public void displayEvent() {
        System.out.println(this.getClass().getName().toLowerCase() +" : " + Integer.toString(this.ID));
    }

    public void takeTurn() {
        System.out.println("provide next stop on route along with the distance");
    }

    //TODORUSHABH: need to understand how this next location works
    public Integer getNextLocation(int routeLocation) {
        int routeSize = this.stopsOnRoute.size();
        if (routeSize > 0) { return (routeLocation + 1) % routeSize; }
        return -1;
    }

    public Integer getStopID(int routeLocation) { return this.stopsOnRoute.get(routeLocation)[0]; }

    public Integer getLength() { return this.stopsOnRoute.size(); }

    public void displayInternalStatus() {
        System.out.print("> route - ID: " + Integer.toString(ID));
        System.out.print(" number: " + Integer.toString(routeNumber) + " name: " + routeName);
        System.out.print(" stops: [ ");
        for (int i = 0; i < stopsOnRoute.size(); i++) {
            System.out.print(Integer.toString(i) + ":" + Integer.toString(stopsOnRoute.get(i)[0]) + " ");
        }
        System.out.println("]");
    }

    //Override the equals method to compare the object
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            VehicleRoute me = (VehicleRoute) object;
            if (this.ID == me.getID()) {
                result = true;
            }
        }
        return result;
    }

}
