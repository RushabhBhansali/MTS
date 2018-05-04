package edu.gatech;

import java.util.Random;
import java.util.HashMap;

public class BusStop {
    private Integer ID;
    private String stopName;
    private Double xCoord;
    private Double yCoord;
    private Random randGenerator;
    private HashMap<Integer, int[]> rateCatchingBus;
    private HashMap<Integer, int[]> rateLeavingBus;
    private Integer waiting;

    public BusStop() {
        this.ID = -1;
    }

    public BusStop(int uniqueValue) {
        this.ID = uniqueValue;
        this.stopName = "";
        this.xCoord = 0.0;
        this.yCoord = 0.0;
        randGenerator = new Random();
        rateCatchingBus = new HashMap<Integer, int[]>();
        rateLeavingBus = new HashMap<Integer, int[]>();
        this.waiting = 0;
    }

    public BusStop(int uniqueValue, String inputName, int inputRiders, double inputXCoord, double inputYCoord) {
        this.ID = uniqueValue;
        this.stopName = inputName;
        this.xCoord = inputXCoord;
        this.yCoord = inputYCoord;
        randGenerator = new Random();
        rateCatchingBus = new HashMap<Integer, int[]>();
        rateLeavingBus = new HashMap<Integer, int[]>();
        this.waiting = inputRiders;
   }

    public void setName(String inputName) { this.stopName = inputName; }

    public void setRiders(int inputRiders) { this.waiting = inputRiders; }

    public void setXCoord(double inputXCoord) { this.xCoord = inputXCoord; }

    public void setYCoord(double inputYCoord) { this.yCoord = inputYCoord; }

    public Integer getID() { return this.ID; }

    public String getName() { return this.stopName; }

    public Integer getWaiting() { return this.waiting; }

    public Double getXCoord() { return this.xCoord; }

    public Double getYCoord() { return this.yCoord; }

    public void displayEvent() {
        System.out.println(" bus stop: " + Integer.toString(this.ID));
    }

    public void takeTurn() {
        System.out.println("get new people - exchange with bus when it passes by");
    }

    public Double findDistance(BusStop destination) {
        // coordinates are measure in abstract units and conversion factor translates to statute miles
        final double distanceConversion = 70.0;
        return distanceConversion * Math.sqrt(Math.pow((this.xCoord - destination.getXCoord()), 2) + Math.pow((this.yCoord - destination.getYCoord()), 2));
    }

    public Integer exchangeRiders(int rank, int initialPassengerCount, int capacity) {
        int hourOfTheDay = (rank / 60) % 24;
        int ableToBoard;
        int[] leavingBusRates, catchingBusRates;
        int[] filler = new int[]{0, 1, 1};

        // calculate expected number riders leaving the bus
        if (rateLeavingBus.containsKey(hourOfTheDay)) { leavingBusRates = rateLeavingBus.get(hourOfTheDay); }
        else { leavingBusRates = filler; }
        int leavingBus = randomBiasedValue(leavingBusRates[0], leavingBusRates[1], leavingBusRates[2]);

        // update the number of riders actually leaving the bus versus the current number of passengers
        int updatedPassengerCount = Math.max(0, initialPassengerCount - leavingBus);

        // calculate expected number riders leaving the bus
        if (rateCatchingBus.containsKey(hourOfTheDay)) { catchingBusRates = rateCatchingBus.get(hourOfTheDay); }
        else { catchingBusRates = filler; }
        int catchingBus = randomBiasedValue(catchingBusRates[0], catchingBusRates[1], catchingBusRates[2]);

        // determine how many of the currently waiting and new passengers will fit on the bus
        int tryingToBoard = waiting + catchingBus;
        int availableSeats = capacity - updatedPassengerCount;

        // update the number of passengers left waiting for the next bus
        if (tryingToBoard > availableSeats) {
            ableToBoard = availableSeats;
            waiting = tryingToBoard - availableSeats;
        } else {
            ableToBoard = tryingToBoard;
            waiting = 0;
        }

        // update the number of riders actually catching the bus and return the difference from the original riders
        int finalPassengerCount = updatedPassengerCount + ableToBoard;
        return finalPassengerCount - initialPassengerCount;
    }

    public void addNewRiders(int moreRiders) { waiting = waiting + moreRiders; }

    public void displayInternalStatus() {
        System.out.print("> stop - ID: " + Integer.toString(ID));
        System.out.print(" name: " + stopName + " waiting: " + Integer.toString(waiting));
        System.out.println(" xCoord: " + Double.toString(xCoord) + " yCoord: " + Double.toString(yCoord));
    }

    public void addArrivalInfo(int timeSlot, int minOn, int avgOn, int maxOn, int minOff, int avgOff, int maxOff) {
        rateCatchingBus.put(timeSlot, new int[]{minOn, avgOn, maxOn});
        rateLeavingBus.put(timeSlot, new int[]{minOff, avgOff, maxOff});
    }

    private int randomBiasedValue(int lower, int middle, int upper) {
        int lowerRange = randGenerator.nextInt(middle - lower + 1) + lower;
        int upperRange = randGenerator.nextInt(upper - middle + 1) + middle;
        return (lowerRange + upperRange) /2;
    }

    //Override the equals method to compare the object
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            BusStop me = (BusStop) object;
            if (this.ID == me.getID()) {
                result = true;
            }
        }
        return result;
    }

}
