package edu.gatech;

public class Rider {

    private int targetRouteID; //RB: routeId of the rider, used to determine the bus/train where rider will get on, used in Vehicle.adjustPassenger()

    private int targetStopID; //RB: stopId where rider will get off the bus/train

    public Rider(int targetRouteID, int targetStopID) { //Changed Integer to int for all the values, will need to do design doc chages for this.
        this.targetRouteID = targetRouteID;
        this.targetStopID = targetStopID;
    }

    public int getTargetRouteID() {
        return targetRouteID;
    }

    public void setTargetRouteID(int targetRouteID) {
        this.targetRouteID = targetRouteID;
    }

    public  int getTargetStopID(){
        return targetStopID;
    }

    /* need to do more research on this
    //this method will be called to destroy Rider object (or delete the reference to the rider object to be more precise)
    public void destroyRiderObject() throws Throwable   {
        this.finalize();   //object reference is set to null and object will eventually deleted by garbage collector,
        //reference: https://stackoverflow.com/questions/5757552/deleting-an-object-in-java
    }
    */
}
