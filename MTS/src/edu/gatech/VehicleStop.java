package edu.gatech;

import java.util.*;

public abstract class VehicleStop {
    protected int ID;
    protected String stopName;
    protected Double xCoord;
    protected Double yCoord;
    protected List<Rider> riders = new ArrayList<>(); //Riders waiting at the stop

    public void setName(String inputName) { this.stopName = inputName; }

    public void setXCoord(double inputXCoord) { this.xCoord = inputXCoord; }

    public void setYCoord(double inputYCoord) { this.yCoord = inputYCoord; }

    public int getID() { return this.ID; }

    public String getName() { return this.stopName; }

    public Double getXCoord() { return this.xCoord; }

    public Double getYCoord() { return this.yCoord; }

    public List<Rider> getRiders() {
        return riders;
    }

    public int getNumberOfRiders() {
        return riders.size();
    }

    public void displayEvent() {
        System.out.println(" Vehicle stop: " + Integer.toString(this.ID));
    }

    /*
    public void takeTurn() {
        System.out.println("get new people - exchange with "+ this.getClass().getName().toLowerCase() +" when it passes by");
    }
*/
    /*
    public Double findDistance(VehicleStop destination) {
        // coordinates are measure in abstract units and conversion factor translates to statute miles
        final double distanceConversion = 70.0;
        return distanceConversion * Math.sqrt(Math.pow((this.xCoord - destination.getXCoord()), 2) + Math.pow((this.yCoord - destination.getYCoord()), 2));
    }
    */

    //exchangeRiders() - PSUEDOCODE
    //1. check each passenger in the active vehicle and remove them from the vehicle if they are at the right stop
    //2. check each rider waiting on the stop
    //      if the riders intended route is same as active vehicle's route
    //          if vehicle has capacity > no of passengers
    //              remove those riders from the stop and put them in the vehicle


    public int exchangeRiders(Vehicle activeVehicle) {
        /* this loop will give concurrent exception error
        if(!activeVehicle.passengers.isEmpty()){
            for(Rider item: activeVehicle.passengers){
                if(item.getTargetStopID() == this.ID){
                    activeVehicle.passengers.remove(item);
                    // item.destroyRiderObject(); //this will be uncommented after destrorRiderObject is implemented;
                }
            }
        }
        */
        try{
            if(!activeVehicle.passengers.isEmpty()){
                Iterator<Rider> iter = activeVehicle.passengers.iterator();
                while(iter.hasNext()){
                    Rider item = iter.next();
                    if(item.getTargetStopID() == this.ID){
                        iter.remove();
                        int currentStopID = this.ID;
                        int targetRouteID = activeVehicle.getRouteID();
                        String[] tokenArray = new String[]{"add_rider_random_targetStop",activeVehicle.getClass().getSimpleName().toLowerCase(),Integer.toString(currentStopID),Integer.toString(1),Integer.toString(targetRouteID)};
                        SimDriver.getSimDriver().processTokens(tokenArray); //add_rider,bus/train,stopID,noOfRiders,targetROuteID
                    }
                }
            }

            Iterator<Rider> iter2 = this.riders.iterator();
            while(iter2.hasNext()){
                Rider item = iter2.next();
                if(item.getTargetRouteID() == activeVehicle.route){
                    if(activeVehicle.passengers.size() < activeVehicle.capacity){
                        activeVehicle.passengers.add(item);
                        //riders.remove(item);
                        iter2.remove();
                    }//impplementation of gideon's random feature
                    else if(activeVehicle.passengers.size() < activeVehicle.overloadCap){
                        double probability = (activeVehicle.overloadCap - activeVehicle.passengers.size())/(double)(activeVehicle.overloadCap - activeVehicle.capacity + 1);
                        if(Math.random() <= probability){
                            activeVehicle.passengers.add(item);
                            iter2.remove();
                        }
                    }
                    else{
                        break;
                    }
                }

            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

       return 1;
    }

    //this operation will be used to instantiate new riders at a particulat VehicleStop.
    //with one call to this operation we can create specified number of riders of one kind (one kind = riders that has the same targetrouteID and targetStopID)
    public void makeRiders( int noOfRiders, int targetStopID, int targetRouteID ) {
        for(int n =1; n <=noOfRiders; n++){
            Rider rider = new Rider(targetRouteID,targetStopID);
            this.riders.add(rider);
        }
    }

    //this operation will be used if already instantiated riders want to be added at the VehicleStop
    public void addNewRiders(List<Rider> moreRiders) { riders.addAll(moreRiders); }

    public void displayInternalStatus() {
        System.out.print("> stop - ID: " + Integer.toString(ID));
        System.out.print(" name: " + stopName + " riders: " + Integer.toString(riders.size()));
        System.out.println(" xCoord: " + Double.toString(xCoord) + " yCoord: " + Double.toString(yCoord));
        System.out.println(" no Of Riders: "+ Integer.toString(riders.size()));
    }

    //Override the equals method to compare the object
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            VehicleStop me = (VehicleStop) object;
            if (this.ID == me.getID()) {
                result = true;
            }
        }
        return result;
    }

}
